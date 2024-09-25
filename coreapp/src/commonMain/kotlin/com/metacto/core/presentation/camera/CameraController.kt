package com.metacto.core.presentation.camera

import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.camera.models.VideoRecordingResult

expect class CameraController {
    fun toggleCameraLens()

    fun getCameraLens(): CameraLens

    @Throws(Throwable::class)
    suspend fun recordVideo(params: VideoRecordingParams)

    @Throws(Throwable::class)
    suspend fun stopRecording(): VideoRecordingResult

    fun isRecording(): Boolean
}