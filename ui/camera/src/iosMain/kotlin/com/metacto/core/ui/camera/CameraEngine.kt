package com.metacto.core.ui.camera

import com.metacto.core.extensions.resumeIfActive
import com.metacto.core.extensions.runOnIOThread
import com.metacto.core.ui.camera.models.CameraLens
import com.metacto.core.ui.camera.models.VideoRecordingParams
import com.metacto.core.ui.camera.models.VideoRecordingResult
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
import platform.Foundation.temporaryDirectory
import platform.UIKit.UIView
import platform.UIKit.UIViewController

actual class CameraEngine(
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

    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLoad() {
        super.viewDidLoad()
        setupSession()
        setupPreviewLayer(view)
        startSession()
        onError = { error ->
            error.printStackTrace()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupSession() {
        captureSession = AVCaptureSession()
        captureSession?.beginConfiguration()

        // Setup inputs with direct device acquisition
        val videoDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
        val audioDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeAudio)

        // Find the back and front cameras for toggle functionality
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

        // Create inputs for camera and audio
        val cameraInput = AVCaptureDeviceInput(device = currentCameraDevice!!, error = null)
        val audioInput = AVCaptureDeviceInput(device = audioDevice!!, error = null)

        if (captureSession?.canAddInput(cameraInput) == true) {
            captureSession?.addInput(cameraInput)
        }
        if (captureSession?.canAddInput(audioInput) == true) {
            captureSession?.addInput(audioInput)
        }

        // Setup movie output
        movieOutput = AVCaptureMovieFileOutput()
        if (captureSession?.canAddOutput(movieOutput!!) == true) {
            captureSession?.addOutput(movieOutput!!)
        }

        captureSession?.commitConfiguration()
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

    @OptIn(ExperimentalForeignApi::class)
    actual fun toggleCameraLens() {
        captureSession?.beginConfiguration()

        // Remove current input
        val currentInputs = captureSession?.inputs
        currentInputs?.forEach { input ->
            if (input is AVCaptureDeviceInput && input.device.hasMediaType(AVMediaTypeVideo)) {
                captureSession?.removeInput(input)
            }
        }

        // Toggle between front and back camera
        currentCamera = if (currentCamera == CameraLens.BACK) CameraLens.FRONT else CameraLens.BACK
        currentCameraDevice = when (currentCamera) {
            CameraLens.FRONT -> frontCamera
            CameraLens.BACK -> backCamera
        }

        // Add new camera input
        val newCameraInput = AVCaptureDeviceInput(device = currentCameraDevice!!, error = null)
        if (captureSession?.canAddInput(newCameraInput) == true) {
            captureSession?.addInput(newCameraInput)
        }

        captureSession?.commitConfiguration()
    }

    actual fun getCameraLens(): CameraLens {
        return currentCamera
    }

    @OptIn(ExperimentalForeignApi::class)
    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) = suspendCancellableCoroutine { continuation ->
        // Create the video file
        val videoFile = getVideosDir().URLByAppendingPathComponent(params.fileName)

        // Delete the video file if exists
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

    actual fun getVideosDirPath(): String {
        return getVideosDir().path!!
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getVideosDir(): NSURL {
        val fileManager = NSFileManager.defaultManager

        // Create the "camera_recorder" directory path
        val videosDir = fileManager
            .temporaryDirectory
            .URLByAppendingPathComponent("camera_recorder")

        // Check if the directory exists, if not, create it
        if (!fileManager.fileExistsAtPath(videosDir?.path!!)) {
            fileManager.createDirectoryAtPath(
                path = videosDir.path!!,
                withIntermediateDirectories = true,
                attributes = null,
                error = null
            )
        }

        return videosDir
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