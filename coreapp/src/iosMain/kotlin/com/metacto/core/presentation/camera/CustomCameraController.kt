package com.metacto.core.presentation.camera

import com.metacto.core.utils.extensions.runOnIOThread
import com.metacto.core.utils.extensions.runOnMainThread
import platform.AVFoundation.*
import platform.Foundation.*
import platform.UIKit.*
import platform.darwin.*
import kotlinx.cinterop.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class CustomCameraController : NSObject(), AVCapturePhotoCaptureDelegateProtocol, AVCaptureFileOutputRecordingDelegateProtocol {

    private var captureSession: AVCaptureSession? = null
    private var backCamera: AVCaptureDevice? = null
    private var frontCamera: AVCaptureDevice? = null
    var currentCamera: AVCaptureDevice? = null
    private var photoOutput: AVCapturePhotoOutput? = null
    private var movieOutput: AVCaptureMovieFileOutput? = null
    var cameraPreviewLayer: AVCaptureVideoPreviewLayer? = null

    private var isUsingFrontCamera = false

    // Output handling closures
    var onPhotoCapture: ((UIImage?) -> Unit)? = null
    var onVideoCapture: ((NSURL?) -> Unit)? = null // Callback for video file
    var onError: ((Exception) -> Unit)? = null

    // Flash mode: Can be auto, on, or off
    var flashMode: AVCaptureFlashMode = AVCaptureFlashModeAuto

    fun setupSession() {
        captureSession = AVCaptureSession()
        captureSession?.beginConfiguration()

        // Setup inputs
        setupInputs()

        // Setup photo output
        /*photoOutput = AVCapturePhotoOutput()
        photoOutput?.setHighResolutionCaptureEnabled(true)
        captureSession?.addOutput(photoOutput!!)*/

        // Setup movie output for video recording
        movieOutput = AVCaptureMovieFileOutput()
        captureSession?.addOutput(movieOutput!!)
//        if (captureSession?.canAddOutput(movieOutput!!) == true) {
//            captureSession?.addOutput(movieOutput!!)
//        }

        captureSession?.commitConfiguration()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupInputs() {
        // Find the back and front cameras
        val availableDevices = AVCaptureDeviceDiscoverySession.discoverySessionWithDeviceTypes(
            listOf(AVCaptureDeviceTypeBuiltInWideAngleCamera),
            AVMediaTypeVideo,
            AVCaptureDevicePositionUnspecified
        ).devices

        for (device in availableDevices) {
            when ((device as AVCaptureDevice).position) {
                AVCaptureDevicePositionBack -> backCamera = device
                AVCaptureDevicePositionFront -> frontCamera = device
            }
        }

        // Set the back camera as default
        currentCamera = backCamera

        // Add input to session
        try {
            val input = AVCaptureDeviceInput.deviceInputWithDevice(backCamera!!, null) as AVCaptureDeviceInput
            if (captureSession?.canAddInput(input) == true) {
                captureSession?.addInput(input)
            }
        } catch (e: Exception) {
            onError?.let { it(e) }
        }
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
    fun setupPreviewLayer(view: UIView) {
        captureSession?.let { captureSession ->
            cameraPreviewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
            cameraPreviewLayer?.videoGravity = AVLayerVideoGravityResizeAspectFill
            cameraPreviewLayer?.setFrame(view.bounds)
            view.layer.addSublayer(cameraPreviewLayer!!)
        }
    }

    // Flash Handling
    fun setFlashMode(mode: AVCaptureFlashMode) {
        flashMode = mode
    }

    // Capture Image
    fun captureImage() {
        val settings = AVCapturePhotoSettings()
        settings.flashMode = flashMode
        settings.isHighResolutionPhotoEnabled()
        photoOutput?.capturePhotoWithSettings(settings, delegate = this)
    }

    // Video Recording Functions
    fun startVideoRecording() {
        val tempDir = NSTemporaryDirectory()
//        val documentDir = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first() as String
//        val filePath = "$documentDir/temp_video.mp4"

        val documentDir = NSFileManager.defaultManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as NSURL
        val filePath = documentDir.URLByAppendingPathComponent("temp_video.mp4")
        val outputURL = NSFileManager.defaultManager.temporaryDirectory.URLByAppendingPathComponent("output.mp4")

        val cacheDirectory: NSURL? = NSSearchPathForDirectoriesInDomains(
            directory = NSCachesDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        ).firstOrNull()?.let { path ->
            (path as? String)?.let {
                NSURL.fileURLWithPath(path).URLByAppendingPathComponent("syncFiles")
            }
        }



        //val fileURL = NSURL.fileURLWithPath(filePath)

        movieOutput?.startRecordingToOutputFileURL(outputURL!!, recordingDelegate = this)
    }

    fun stopVideoRecording() {
        movieOutput?.stopRecording()
    }

    fun isVideoRecording(): Boolean {
        return movieOutput?.isRecording() == true
    }

    // Switch Camera
    @OptIn(ExperimentalForeignApi::class)
    fun switchCamera() {
        captureSession?.beginConfiguration()

        // Remove current input
        val currentInput = captureSession?.inputs?.first() as? AVCaptureDeviceInput
        if (currentInput != null) {
            captureSession?.removeInput(currentInput)
        }

        // Toggle between front and back camera
        isUsingFrontCamera = !isUsingFrontCamera
        currentCamera = if (isUsingFrontCamera) frontCamera else backCamera

        // Add new input
        try {
            val newInput = AVCaptureDeviceInput.deviceInputWithDevice(currentCamera!!, null) as AVCaptureDeviceInput
            if (captureSession?.canAddInput(newInput) == true) {
                println("can add video input")
                captureSession?.addInput(newInput)
            } else {
                println("can't add video input")
            }
        } catch (e: Exception) {
            println("error adding video input")
            e.printStackTrace()
            onError?.invoke(e)
        }

        // Add audio input to session
        try {
            val audioDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeAudio)
            val audioInput = AVCaptureDeviceInput.deviceInputWithDevice(audioDevice!!, null) as AVCaptureDeviceInput
            if (captureSession?.canAddInput(audioInput) == true) {
                captureSession?.addInput(audioInput)
                println("can add audio input")
            } else {
                println("can't add audio input")
            }
        } catch (e: Exception) {
            println("can't add audio input ${e.message}")
            onError?.let { it(e) }
        }

        // Adjust connection settings for mirroring
        val connection = cameraPreviewLayer?.connection
        if (connection?.isVideoMirroringSupported() == true) {
            connection.automaticallyAdjustsVideoMirroring = false
            connection.setVideoMirrored(isUsingFrontCamera)
        }

        captureSession?.commitConfiguration()
    }

    // AVCaptureFileOutputRecordingDelegate
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

    // AVCapturePhotoCaptureDelegate
    override fun captureOutput(
        output: AVCapturePhotoOutput,
        didFinishProcessingPhoto: AVCapturePhoto,
        error: NSError?
    ) {
        if (error != null) {
            onError?.invoke(Exception(error.localizedDescription))
            return
        }

        val imageData = didFinishProcessingPhoto.fileDataRepresentation()
        val image = imageData?.let { UIImage(data = it) }
        onPhotoCapture?.invoke(image)
    }
}
