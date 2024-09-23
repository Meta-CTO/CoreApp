package com.metacto.core.presentation.camera

import com.metacto.core.presentation.camera.models.CameraFlashMode
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.CameraRotation
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult

actual class CameraController {

    actual fun toggleFlashMode() {
        TODO("Not yet implemented")
    }

    actual fun toggleCameraLens() {
        TODO("Not yet implemented")
    }

    actual fun getFlashMode(): CameraFlashMode {
        TODO("Not yet implemented")
    }

    actual fun getCameraLens(): CameraLens {
        TODO("Not yet implemented")
    }

    actual fun getCameraRotation(): CameraRotation {
        TODO("Not yet implemented")
    }

    actual fun setCameraRotation(rotation: CameraRotation) {
        TODO("Not yet implemented")
    }

    @Throws(Throwable::class)
    actual suspend fun recordVideo(params: VideoRecordingParams) {
        TODO("Not yet implemented")
    }

    @Throws(Throwable::class)
    actual suspend fun stopRecording(): VideoRecordingResult {
        TODO("Not yet implemented")
    }
}