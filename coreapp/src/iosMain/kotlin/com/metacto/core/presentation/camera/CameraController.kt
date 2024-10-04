package com.metacto.core.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult
import com.metacto.strapikmm.util.resumeIfActive
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.temporaryDirectory
import platform.UIKit.UIViewController

actual class CameraController : UIViewController(nibName = null, bundle = null) {

    private lateinit var cameraController: CustomCameraController

    // Variables to store the current camera settings
    private var currentCameraLens: CameraLens = CameraLens.BACK

    override fun viewDidLoad() {
        super.viewDidLoad()
        cameraController = CustomCameraController()
        cameraController.setupSession()
        cameraController.setupPreviewLayer(view)
        cameraController.startSession()
        configureCameraCallbacks()
    }

    private fun configureCameraCallbacks() {
        cameraController.onError = { error ->
            println("Camera Error: $error")
            error.printStackTrace()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        cameraController.cameraPreviewLayer?.setFrame(view.bounds)
    }

    actual fun toggleCameraLens() {
        currentCameraLens = if (currentCameraLens == CameraLens.BACK) CameraLens.FRONT else CameraLens.BACK
        cameraController.switchCamera()
    }

    actual fun getCameraLens(): CameraLens {
        return currentCameraLens
    }

    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) {
        return suspendCancellableCoroutine { continuation ->
            cameraController.onVideoCapture = { url ->
                println("Video captured: $url")
            }
            cameraController.startVideoRecording()
            continuation.resumeIfActive(Unit)
        }
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording(): VideoRecordingResult = suspendCancellableCoroutine { cont ->
//        val documentDir = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first() as String
//        val filePath = "$documentDir/temp_video.mp4"
//        val fileURL = NSURL.fileURLWithPath(filePath)

        val documentDir = NSFileManager.defaultManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as NSURL
        val filePath = documentDir.URLByAppendingPathComponent("temp_video.mp4")
        val outputURL = NSFileManager.defaultManager.temporaryDirectory.URLByAppendingPathComponent("output.mp4")

        cameraController.stopVideoRecording()

        cont.resumeIfActive(VideoRecordingResult(videoPath = outputURL!!.path.orEmpty()))
    }

    actual fun isRecording(): Boolean {
        return cameraController.isVideoRecording()
    }
}

@Composable
actual fun rememberCameraController(): CameraController {
    return remember {
        CameraController()
    }
}