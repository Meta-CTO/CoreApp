package com.metacto.core.presentation.camera.models

data class VideoRecordingParams(
    val withAudio: Boolean = true,
    val quality: VideoQuality = VideoQuality.DEFAULT
)
