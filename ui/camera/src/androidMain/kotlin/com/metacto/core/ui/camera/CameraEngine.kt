package com.metacto.core.ui.camera

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
import com.metacto.core.extensions.exceptionIfActive
import com.metacto.core.extensions.orFalse
import com.metacto.core.extensions.resumeIfActive
import com.metacto.core.ui.camera.models.CameraLens
import com.metacto.core.ui.camera.models.VideoRecordingParams
import com.metacto.core.ui.camera.models.VideoRecordingResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

actual class CameraEngine(
    private val context: Context,
    actual val defaultCamera: CameraLens = CameraLens.BACK
) {
    private var previewView: PreviewView? = null
    private var cameraPreview: Preview? = null
    private var camera: Camera? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null
    private var currentOutputFile: File? = null
    private var currentCamera: CameraLens = defaultCamera

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
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            // Build video capture
            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HD))
                .build()
            videoCapture = VideoCapture.withOutput(recorder)

            // Bind camera to life cycle
            bindCameraToLifeCycle()

        }, ContextCompat.getMainExecutor(context))
    }

    actual fun toggleCameraLens() {
        currentCamera = if (currentCamera == CameraLens.BACK) {
            CameraLens.FRONT
        } else {
            CameraLens.BACK
        }
        previewView?.let { startCamera(it) }
    }

    actual fun getCameraLens(): CameraLens {
        return defaultCamera
    }

    @SuppressLint("MissingPermission")
    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) {
        return suspendCancellableCoroutine { cont ->
            // Validate video capture
            val videoCapture = videoCapture ?: run {
                cont.exceptionIfActive(Throwable("VideoCapture is not created yet"))
                return@suspendCancellableCoroutine
            }

            // Create recording
            currentOutputFile = createVideoFile(fileName = params.fileName)
            val outputOptions = FileOutputOptions.Builder(currentOutputFile!!).build()
            val pendingRecording = videoCapture.output
                .prepareRecording(context, outputOptions)
                .withAudioEnabled()

            // Start recording
            recording = pendingRecording.start(ContextCompat.getMainExecutor(context)) { event ->
                if (event is VideoRecordEvent.Start) {
                    cont.resumeIfActive(Unit)
                }
            }
        }
    }

    private fun createVideoFile(fileName: String): File {
        return File(getVideosDir(), fileName)
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording(): VideoRecordingResult {
        // Stop the video recording
        recording?.stop()
        recording = null

        // Validate output file
        val outputFile = requireNotNull(currentOutputFile) {
            "Error saving video file"
        }

        // Add some delay before returning allow the video file to be saved
        delay(STOP_RECORDING_DELAY)

        // Return the result
        return VideoRecordingResult(
            videoPath = outputFile.absolutePath
        )
    }

    @SuppressLint("RestrictedApi")
    actual fun isRecording(): Boolean {
        return recording?.isClosed.orFalse().not()
    }

    actual fun getVideosDirPath(): String {
        return getVideosDir().absolutePath
    }

    private fun getVideosDir(): File {
        return File(context.cacheDir.absolutePath + "/camera_recorder").apply {
            if (exists().not()) {
                mkdirs()
            }
        }
    }

    // Bind camera to lifecycle with image and video capture
    private fun bindCameraToLifeCycle() {
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(currentCamera.toCameraXLensFacing())
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
        private val STOP_RECORDING_DELAY = 300.milliseconds
    }
}