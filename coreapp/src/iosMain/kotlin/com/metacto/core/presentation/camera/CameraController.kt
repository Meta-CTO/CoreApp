package com.metacto.core.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult
import com.metacto.core.utils.extensions.runOnIOThread
import com.metacto.strapikmm.util.resumeIfActive
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceDiscoverySession
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureDevicePositionUnspecified
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInWideAngleCamera
import platform.AVFoundation.AVCaptureFileOutput
import platform.AVFoundation.AVCaptureFileOutputRecordingDelegateProtocol
import platform.AVFoundation.AVCaptureMovieFileOutput
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureVideoOrientationPortrait
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeAudio
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.position
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.temporaryDirectory
import platform.UIKit.UIView
import platform.UIKit.UIViewController

actual class CameraController(
    actual val defaultCamera: CameraLens = CameraLens.BACK
) : UIViewController(nibName = null, bundle = null), AVCaptureFileOutputRecordingDelegateProtocol {

    private var currentCamera: CameraLens = defaultCamera
    private var captureSession: AVCaptureSession? = null
    private var currentCameraDevice: AVCaptureDevice? = null
    private var backCamera: AVCaptureDevice? = null
    private var frontCamera: AVCaptureDevice? = null
    private var movieOutput: AVCaptureMovieFileOutput? = null
    private var cameraPreviewLayer: AVCaptureVideoPreviewLayer? = null
    private var onVideoCapture: ((NSURL?) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null

    override fun viewDidLoad() {
        super.viewDidLoad()
        setupSession()
        setupPreviewLayer(view)
        startSession()
        onError = { error ->
            error.printStackTrace()
        }
    }

    private fun setupSession() {
        captureSession = AVCaptureSession()
        captureSession?.beginConfiguration()

        // Setup inputs
        setupInputs()

        // Setup movie output for video recording
        movieOutput = AVCaptureMovieFileOutput()
        if (captureSession?.canAddOutput(movieOutput!!) == true) {
            captureSession?.addOutput(movieOutput!!)
        }

        // Commit configurations
        captureSession?.commitConfiguration()
    }

    private fun setupInputs() {
        // Find the back and front cameras
        val availableDevices = AVCaptureDeviceDiscoverySession.discoverySessionWithDeviceTypes(
            listOf(AVCaptureDeviceTypeBuiltInWideAngleCamera),
            AVMediaTypeVideo,
            AVCaptureDevicePositionUnspecified
        ).devices

        // Set back and front cameras
        for (device in availableDevices) {
            when ((device as AVCaptureDevice).position) {
                AVCaptureDevicePositionBack -> backCamera = device
                AVCaptureDevicePositionFront -> frontCamera = device
            }
        }

        // Set current camera
        currentCameraDevice = when (defaultCamera) {
            CameraLens.FRONT -> frontCamera
            CameraLens.BACK -> backCamera
        }

        // Add inputs
        addCameraInputToCurrentSession()
        addAudioInputToCurrentSession()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun addCameraInputToCurrentSession() {
        try {
            val input = AVCaptureDeviceInput.deviceInputWithDevice(
                currentCameraDevice!!,
                null
            ) as AVCaptureDeviceInput
            if (captureSession?.canAddInput(input) == true) {
                captureSession?.addInput(input)
            }
        } catch (e: Throwable) {
            onError?.let { it(e) }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun addAudioInputToCurrentSession() {
        try {
            val audioDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeAudio)
            val audioInput = AVCaptureDeviceInput.deviceInputWithDevice(
                audioDevice!!,
                null
            ) as AVCaptureDeviceInput
            if (captureSession?.canAddInput(audioInput) == true) {
                captureSession?.addInput(audioInput)
            }
        } catch (e: Throwable) {
            onError?.let { it(e) }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupPreviewLayer(view: UIView) {
        val captureSession = this.captureSession ?: return

        cameraPreviewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
        cameraPreviewLayer?.videoGravity = AVLayerVideoGravityResizeAspectFill
        cameraPreviewLayer?.setFrame(view.bounds)
        view.layer.addSublayer(cameraPreviewLayer!!)
    }

    private fun startSession() = runOnIOThread {
        if (captureSession?.isRunning() == false) {
            captureSession?.startRunning()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        cameraPreviewLayer?.setFrame(view.bounds)
    }

    actual fun toggleCameraLens() {
        captureSession?.beginConfiguration()

        // Remove current input
        val currentInput = captureSession?.inputs?.firstOrNull() as? AVCaptureDeviceInput
        if (currentInput != null) {
            captureSession?.removeInput(currentInput)
        }

        // Toggle between front and back camera
        currentCamera = if (currentCamera == CameraLens.BACK) CameraLens.FRONT else CameraLens.BACK
        currentCameraDevice = when(this.currentCamera) {
            CameraLens.FRONT -> frontCamera
            CameraLens.BACK -> backCamera
        }

        // Add inputs
        addCameraInputToCurrentSession()
        addAudioInputToCurrentSession()

        // Commit configuration
        captureSession?.commitConfiguration()
    }

    actual fun getCameraLens(): CameraLens {
        return currentCamera
    }

    @OptIn(ExperimentalForeignApi::class)
    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) = suspendCancellableCoroutine { continuation ->
        // Create the video file
        val videoFile = NSFileManager.defaultManager
            .temporaryDirectory
            .URLByAppendingPathComponent(params.fileName)

        // Delete the video file if exists before so that the new video could be recorded
        NSFileManager.defaultManager.run {
            if (fileExistsAtPath(videoFile?.path!!)) {
                removeItemAtURL(videoFile, null)
            }
        }

        // Configure the movie connection before start recording
        movieOutput?.connectionWithMediaType(AVMediaTypeVideo)?.let {
            // Set video orientation
            if (it.isVideoOrientationSupported()) {
                it.videoOrientation = cameraPreviewLayer?.connection?.videoOrientation
                    ?: AVCaptureVideoOrientationPortrait
            }

            // Set video mirroring
            if (it.isVideoMirroringSupported()) {
                it.setVideoMirrored(isUsingFrontCamera())
            }
        }

        // Start recording
        movieOutput?.startRecordingToOutputFileURL(videoFile!!, recordingDelegate = this)

        continuation.resumeIfActive(Unit)
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording() = suspendCancellableCoroutine { cont ->
        movieOutput?.stopRecording()
        onVideoCapture = { videoUrl ->
            cont.resumeIfActive(VideoRecordingResult(videoPath = videoUrl?.path!!))
        }
    }

    override fun captureOutput(
        output: AVCaptureFileOutput,
        didFinishRecordingToOutputFileAtURL: NSURL,
        fromConnections: List<*>,
        error: NSError?
    ) {
        if (error != null) {
            onError?.invoke(Exception(error.localizedDescription))
        } else {
            onVideoCapture?.invoke(didFinishRecordingToOutputFileAtURL)
        }
    }

    actual fun isRecording(): Boolean {
        return movieOutput?.isRecording() == true
    }

    override fun viewDidUnload() {
        stopSession()
        super.viewDidUnload()
    }

    private fun stopSession() = runOnIOThread {
        if (captureSession?.isRunning() == true) {
            captureSession?.stopRunning()
        }
    }

    private fun isUsingFrontCamera(): Boolean {
        return currentCamera == CameraLens.FRONT
    }
}

@Composable
actual fun rememberCameraController(defaultCamera: CameraLens): CameraController {
    return remember(defaultCamera) {
        CameraController(
            defaultCamera = defaultCamera
        )
    }
}