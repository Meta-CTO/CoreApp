package com.metacto.core.presentation.camera

import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.permissions.enums.Permission
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult

class CameraController(
    private val permissionManager: IPermissionManager,
    val cameraEngine: CameraEngine
) {
    fun toggleCameraLens() {
        cameraEngine.toggleCameraLens()
    }

    fun getCameraLens(): CameraLens {
        return cameraEngine.getCameraLens()
    }

    @Throws(Throwable::class)
    suspend fun recordVideo(params: VideoRecordingParams) {
        // Request required permissions
        permissionManager.requestPermission(Permission.CAMERA)
        permissionManager.requestPermission(Permission.RECORD_AUDIO)

        // Then record
        cameraEngine.recordVideo(params)
    }

    @Throws(Throwable::class)
    suspend fun stopRecording(): VideoRecordingResult {
        return cameraEngine.stopRecording()
    }

    fun isRecording(): Boolean {
        return cameraEngine.isRecording()
    }
}