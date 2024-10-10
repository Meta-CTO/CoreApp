package com.metacto.core.utils.media

interface IMediaManager {
    suspend fun getVideoPreview(videoPath: String): ByteArray?
}