package com.metacto.core.presentation.camera

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.metacto.core.presentation.camera.models.CameraFlashMode
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.CameraRotation
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult
import com.metacto.core.utils.extensions.orFalse
import com.metacto.strapikmm.util.exceptionIfActive
import com.metacto.strapikmm.util.resumeIfActive
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File

actual class CameraController(
    private val context: Context
) {
    private var previewView: PreviewView? = null
    private var cameraPreview: Preview? = null
    private var camera: Camera? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private var currentFlashMode = CameraFlashMode.OFF
    private var currentCameraLens = CameraLens.BACK
    private var currentRotation = CameraRotation.ROTATION_0

    private val defaultVideoFile by lazy {
        // Get videos dir and create it if not exists
        val videoDir = File(context.cacheDir.absolutePath + "/videos")
        if (videoDir.exists().not()) {
            videoDir.mkdirs()
        }

        // Return the file
        File(videoDir, VIDEO_FILE_NAME)
    }

    fun init(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun startCamera(previewView: PreviewView) {
        this.previewView = previewView
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            // Get camera provider
            cameraProvider = cameraProviderFuture.get()

            // Build camera preview
            cameraPreview = Preview.Builder()
                .setTargetRotation(currentRotation.toSurfaceRotation())
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            // TODO: take quality as a parameter
            // Build video capture
            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HD))
                .build()
            videoCapture = VideoCapture.withOutput(recorder)

            // Bind camera to life cycle
            bindCameraToLifeCycle()

        }, ContextCompat.getMainExecutor(context))
    }

    fun stopCamera() {
        camera?.cameraControl?.
    }

    actual fun toggleFlashMode() {
        // Toggle between ON and OFF
        currentFlashMode = if (currentFlashMode == CameraFlashMode.OFF) {
            CameraFlashMode.ON
        } else {
            CameraFlashMode.OFF
        }
    }

    actual fun toggleCameraLens() {
        currentCameraLens = if (currentCameraLens == CameraLens.BACK) {
            CameraLens.FRONT
        } else {
            CameraLens.BACK
        }
        previewView?.let { startCamera(it) }
    }

    actual fun getFlashMode(): CameraFlashMode {
        return currentFlashMode
    }

    actual fun getCameraLens(): CameraLens {
        return currentCameraLens
    }

    actual fun getCameraRotation(): CameraRotation {
        return currentRotation
    }

    actual fun setCameraRotation(rotation: CameraRotation) {
        // Update rotation
        currentRotation = rotation
        val surfaceRotation = rotation.toSurfaceRotation()
        videoCapture?.targetRotation = surfaceRotation
        cameraPreview?.targetRotation = surfaceRotation

        // Re-bind the camera to apply the changes immediately
        bindCameraToLifeCycle()
    }

    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) = suspendCancellableCoroutine { cont ->
        // Validate video capture
        val videoCapture = videoCapture ?: run {
            cont.exceptionIfActive(Throwable("VideoCapture is not created yet"))
            return@suspendCancellableCoroutine
        }

        // Create recording
        val outputOptions = FileOutputOptions.Builder(defaultVideoFile).build()
        val pendingRecording = videoCapture.output.prepareRecording(context, outputOptions)

        // Start recording
        recording = pendingRecording.start(ContextCompat.getMainExecutor(context)) { event ->
            if (event is VideoRecordEvent.Start) {
                cont.resumeIfActive(Unit)
            }
        }
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording() = suspendCancellableCoroutine { cont ->
        recording?.stop()
        recording = null
        cont.resumeIfActive(
            VideoRecordingResult(
                videoPath = defaultVideoFile.absolutePath
            )
        )
    }

    @SuppressLint("RestrictedApi")
    actual fun isRecording(): Boolean {
        return recording?.isClosed.orFalse().not()
    }

    // Bind camera to lifecycle with image and video capture
    private fun bindCameraToLifeCycle() {
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(currentCameraLens.toCameraXLensFacing())
            .build()

        cameraProvider?.unbindAll()

        // Bind both preview, image capture, and video capture use cases
        camera = cameraProvider?.bindToLifecycle(
            lifecycleOwner!!,
            cameraSelector,
            cameraPreview,
            videoCapture
        )
    }

    companion object {
        private const val VIDEO_FILE_NAME = "recorded_video.mp4"
    }
}