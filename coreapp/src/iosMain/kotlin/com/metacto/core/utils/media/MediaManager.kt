package com.metacto.core.utils.media

import com.metacto.core.utils.extensions.normalizedImage
import com.metacto.core.utils.extensions.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.AVFoundation.AVAsset
import platform.AVFoundation.AVAssetImageGenerator
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSURL
import platform.UIKit.UIImage

class MediaManager : IMediaManager {
    override suspend fun getVideoPreview(videoPath: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            getVideoFrame(videoPath, 1)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getVideoFrame(videoPath: String, timeInSeconds: Long): ByteArray? {
        val url = NSURL.fileURLWithPath(videoPath)
        val asset = AVAsset.assetWithURL(url)
        val imageGenerator = AVAssetImageGenerator(asset)

        val time = CMTimeMake(value = timeInSeconds, timescale = 1)

        return try {
            val cgImage = imageGenerator.copyCGImageAtTime(time, actualTime = null, error = null)
            UIImage(cgImage).normalizedImage().toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}