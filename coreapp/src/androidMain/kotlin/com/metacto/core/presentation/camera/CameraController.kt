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
import com.metacto.core.presentation.camera.models.CameraLens
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
    private var currentCameraLens = CameraLens.BACK
    private var currentOutputFile: File? = null

    private val videosDirectory by lazy {
        File(context.cacheDir.absolutePath + "/videos").apply {
            if (exists().not()) mkdirs()
        }
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
        currentCameraLens = if (currentCameraLens == CameraLens.BACK) {
            CameraLens.FRONT
        } else {
            CameraLens.BACK
        }
        previewView?.let { startCamera(it) }
    }

    actual fun getCameraLens(): CameraLens {
        return currentCameraLens
    }

    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) = suspendCancellableCoroutine { cont ->
        // Validate video capture
        val videoCapture = videoCapture ?: run {
            cont.exceptionIfActive(Throwable("VideoCapture is not created yet"))
            return@suspendCancellableCoroutine
        }

        // Create recording
        currentOutputFile = createVideoFile(fileName = params.fileName)
        val outputOptions = FileOutputOptions.Builder(currentOutputFile!!).build()
        val pendingRecording = videoCapture.output.prepareRecording(context, outputOptions)

        // Start recording
        recording = pendingRecording.start(ContextCompat.getMainExecutor(context)) { event ->
            if (event is VideoRecordEvent.Start) {
                cont.resumeIfActive(Unit)
            }
        }
    }

    private fun createVideoFile(fileName: String): File {
        return File(videosDirectory, fileName)
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording() = suspendCancellableCoroutine { cont ->
        recording?.stop()
        recording = null

        val outputFile = requireNotNull(currentOutputFile) {
            "Error saving video file"
        }

        cont.resumeIfActive(
            VideoRecordingResult(
                videoPath = outputFile.absolutePath
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
}