package com.metacto.core.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult
import com.metacto.strapikmm.util.resumeIfActive
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.UIViewController

actual class CameraController(
    actual val defaultCamera: CameraLens = CameraLens.BACK
) : UIViewController(nibName = null, bundle = null) {

    private lateinit var cameraController: CustomCameraController
    private var currentCamera: CameraLens = defaultCamera

    override fun viewDidLoad() {
        super.viewDidLoad()
        cameraController = CustomCameraController(defaultCameraLens = defaultCamera)
        cameraController.setupSession()
        cameraController.setupPreviewLayer(view)
        cameraController.startSession()
        cameraController.onError = { error ->
            error.printStackTrace()
        }
    }

    override fun viewDidUnload() {
        cameraController.stopSession()
        super.viewDidUnload()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        cameraController.cameraPreviewLayer?.setFrame(view.bounds)
    }

    actual fun toggleCameraLens() {
        currentCamera = if (currentCamera == CameraLens.BACK) CameraLens.FRONT else CameraLens.BACK
        cameraController.switchCamera()
    }

    actual fun getCameraLens(): CameraLens {
        return currentCamera
    }

    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) {
        return suspendCancellableCoroutine { continuation ->
            cameraController.startVideoRecording(params)
            continuation.resumeIfActive(Unit)
        }
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording() = suspendCancellableCoroutine { cont ->
        cameraController.stopVideoRecording()
        cameraController.onVideoCapture = { videoUrl ->
            cont.resumeIfActive(VideoRecordingResult(videoPath = videoUrl?.path!!))
        }
    }

    actual fun isRecording(): Boolean {
        return cameraController.isVideoRecording()
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