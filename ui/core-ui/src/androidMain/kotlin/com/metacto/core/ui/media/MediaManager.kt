package com.metacto.core.ui.media

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.time.Duration.Companion.seconds

class MediaManager(private val context: Context) : IMediaManager {

    override suspend fun getVideoPreview(videoPath: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            getVideoFrame(videoPath, 1)
        }
    }

    private fun getVideoFrame(videoPath: String, timeInSeconds: Long): ByteArray? {
        val retriever = MediaMetadataRetriever()
        return try {
            when {
                // Handle content URIs
                videoPath.startsWith("content://") -> {
                    val uri = videoPath.toUri()
                    retriever.setDataSource(context, uri)
                }

                // Handle regular file paths
                else -> {
                    retriever.setDataSource(videoPath)
                }
            }

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