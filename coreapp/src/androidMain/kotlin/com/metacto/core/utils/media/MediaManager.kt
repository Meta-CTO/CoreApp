package com.metacto.core.utils.media

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.time.Duration.Companion.seconds

class MediaManager : IMediaManager {

    override suspend fun getVideoPreview(videoPath: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            getVideoFrame(videoPath, 1)
        }
    }

    private fun getVideoFrame(videoPath: String, timeInSeconds: Long): ByteArray? {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(videoPath)
            val bitmap = retriever.getFrameAtTime(timeInSeconds.seconds.inWholeMicroseconds)
            bitmap?.let {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.toByteArray()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            retriever.release()
        }
    }
}