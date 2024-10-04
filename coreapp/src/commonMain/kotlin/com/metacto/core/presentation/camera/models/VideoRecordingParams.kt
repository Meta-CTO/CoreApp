package com.metacto.core.presentation.camera.models

data class VideoRecordingParams(
    val withAudio: Boolean = true,
    val directoryPath: String? = null,
    val fileName: String = "recorded_video.mp4"
)
