package com.metacto.core.ui.camera

import com.metacto.core.ui.camera.models.CameraLens
import com.metacto.core.ui.camera.models.VideoRecordingParams
import com.metacto.core.ui.camera.models.VideoRecordingResult
import com.metacto.core.ui.permissions.IPermissionManager
import com.metacto.core.ui.permissions.enums.Permission

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
        permissionManager.requestPermission(
            permission = Permission.CAMERA,
            openAppSettingsIfRequired = false
        )
        permissionManager.requestPermission(
            permission = Permission.RECORD_AUDIO,
            openAppSettingsIfRequired = false
        )

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

    fun getVideosDirPath(): String {
        return cameraEngine.getVideosDirPath()
    }
}