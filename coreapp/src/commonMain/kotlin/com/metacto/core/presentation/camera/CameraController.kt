package com.metacto.core.presentation.camera

import com.metacto.core.presentation.camera.models.CameraFlashMode
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.CameraRotation
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult

expect class CameraController {
    fun toggleFlashMode()

    fun toggleCameraLens()

    fun getFlashMode(): CameraFlashMode

    fun getCameraLens(): CameraLens

    fun getCameraRotation(): CameraRotation

    fun setCameraRotation(rotation: CameraRotation)

    @Throws(Throwable::class)
    suspend fun recordVideo(params: VideoRecordingParams)

    @Throws(Throwable::class)
    suspend fun stopRecording(): VideoRecordingResult

    fun isRecording(): Boolean
}