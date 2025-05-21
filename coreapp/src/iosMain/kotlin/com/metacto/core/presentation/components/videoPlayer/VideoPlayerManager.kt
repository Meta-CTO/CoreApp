package com.metacto.core.presentation.components.videoPlayer

import com.metacto.core.utils.extensions.cleanFilePath
import com.metacto.core.utils.extensions.isValidUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import platform.AVFoundation.AVMetadataCommonIdentifierArtist
import platform.AVFoundation.AVMetadataCommonIdentifierArtwork
import platform.AVFoundation.AVMetadataCommonIdentifierTitle
import platform.AVFoundation.AVMetadataKeySpaceCommon
import platform.AVFoundation.AVMutableMetadataItem
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVURLAsset
import platform.AVFoundation.asset
import platform.AVFoundation.currentItem
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVFoundation.setKeySpace
import platform.AVKit.externalMetadata
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL

internal class VideoPlayerManager : KoinComponent {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val player by lazy {
        AVPlayer()
    }

    fun setMedia(
        videoUrl: String,
        enableMediaMetadata: Boolean,
        videoTitle: String?,
        videoArtist: String?,
        videoArtworkUrl: String?
    ) {
        val cleanedVideoUrl = videoUrl.cleanFilePath()
        val currentItem = player.currentItem
        val currentAssetUrl = (currentItem?.asset as? AVURLAsset)?.URL?.absoluteString

        if (currentAssetUrl == cleanedVideoUrl) {
            // Same video, skip setting new media
            return
        }

        val nsUrl = if (videoUrl.isEmpty() || videoUrl.isValidUrl()) {
            NSURL.URLWithString(videoUrl)!!
        } else {
            NSURL.fileURLWithPath(videoUrl.cleanFilePath())
        }
        val newPlayerItem = AVPlayerItem(uRL = nsUrl)

        val titleMetadata = AVMutableMetadataItem().apply {
            setIdentifier(AVMetadataCommonIdentifierTitle)
            setExtendedLanguageTag("und")
            setValue(videoTitle.orEmpty() as NSString)
        }
        newPlayerItem.externalMetadata = newPlayerItem.externalMetadata.toMutableList().also {
            it.add(titleMetadata)
        }

        if (enableMediaMetadata) {
            val artistMetadata = AVMutableMetadataItem().apply {
                setIdentifier(AVMetadataCommonIdentifierArtist)
                setExtendedLanguageTag("und")
                setValue(videoArtist.orEmpty() as NSString)
            }
            newPlayerItem.externalMetadata = newPlayerItem.externalMetadata.toMutableList().also {
                it.add(titleMetadata)
                it.add(artistMetadata)
            }

            coroutineScope.launch {
                val artworkData = videoArtworkUrl?.let {
                    NSData.dataWithContentsOfURL(NSURL.URLWithString(videoArtworkUrl)!!)
                }

                val artworkMetadata = AVMutableMetadataItem().apply {
                    setIdentifier(AVMetadataCommonIdentifierArtwork)
                    setKeySpace(AVMetadataKeySpaceCommon)
                    setValue(artworkData)
                }

                newPlayerItem.externalMetadata = newPlayerItem.externalMetadata.toMutableList().also {
                    it.add(artworkMetadata)
                }
            }
        }

        player.replaceCurrentItemWithPlayerItem(newPlayerItem)
    }
}