package com.metacto.core.ui.media

interface IMediaManager {
    suspend fun getVideoPreview(videoPath: String): ByteArray?
}