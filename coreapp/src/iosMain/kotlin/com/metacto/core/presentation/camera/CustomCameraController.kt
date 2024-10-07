package com.metacto.core.presentation.camera

import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.utils.extensions.runOnIOThread
import kotlinx.cinterop.ExperimentalForeignApi
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
import platform.darwin.NSObject

class CustomCameraController(
    private val defaultCameraLens: CameraLens
) : NSObject(), AVCaptureFileOutputRecordingDelegateProtocol {

    private var captureSession: AVCaptureSession? = null
    private var backCamera: AVCaptureDevice? = null
    private var frontCamera: AVCaptureDevice? = null
    private var currentCamera: AVCaptureDevice? = null
    private var movieOutput: AVCaptureMovieFileOutput? = null
    var cameraPreviewLayer: AVCaptureVideoPreviewLayer? = null
        private set

    var onVideoCapture: ((NSURL?) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null

    fun setupSession() {
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
        currentCamera = when (defaultCameraLens) {
            CameraLens.FRONT -> frontCamera
            CameraLens.BACK -> backCamera
        }

        // Add inputs
        addCameraInputToCurrentSession()
        addAudioInputToCurrentSession()
    }

    fun switchCamera() {
        captureSession?.beginConfiguration()

        // Remove current input
        val currentInput = captureSession?.inputs?.firstOrNull() as? AVCaptureDeviceInput
        if (currentInput != null) {
            captureSession?.removeInput(currentInput)
        }

        // Toggle between front and back camera
        currentCamera = if (currentCamera == backCamera) frontCamera else backCamera

        // Add inputs
        addCameraInputToCurrentSession()
        addAudioInputToCurrentSession()

        // Commit configuration
        captureSession?.commitConfiguration()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun addCameraInputToCurrentSession() {
        try {
            val input = AVCaptureDeviceInput.deviceInputWithDevice(
                currentCamera!!,
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
    fun setupPreviewLayer(view: UIView) {
        val captureSession = this.captureSession ?: return

        cameraPreviewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
        cameraPreviewLayer?.videoGravity = AVLayerVideoGravityResizeAspectFill
        cameraPreviewLayer?.setFrame(view.bounds)
        view.layer.addSublayer(cameraPreviewLayer!!)
    }

    fun startSession() = runOnIOThread {
        if (captureSession?.isRunning() == false) {
            captureSession?.startRunning()
        }
    }

    fun stopSession() = runOnIOThread {
        if (captureSession?.isRunning() == true) {
            captureSession?.stopRunning()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun startVideoRecording(params: VideoRecordingParams) {
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
    }

    fun stopVideoRecording() {
        movieOutput?.stopRecording()
    }

    fun isVideoRecording(): Boolean {
        return movieOutput?.isRecording() == true
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

    private fun isUsingFrontCamera(): Boolean {
        return currentCamera == frontCamera
    }
}
