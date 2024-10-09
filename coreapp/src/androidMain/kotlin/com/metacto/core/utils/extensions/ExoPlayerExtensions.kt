package com.metacto.core.utils.extensions

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.metacto.strapikmm.util.applyIf

fun Player.kill() {
    stop()
    release()
}

fun createMediaSource(url: String, metaData: MediaMetadata? = null): MediaSource {
    return when {
        url.contains(".m3u8") -> createHlsMediaSource(url, metaData)
        url.isLocalFile() -> createLocalFileMediaSource(url, metaData)
        else -> createProgressiveMediaSource(url, metaData)
    }
}

@OptIn(UnstableApi::class)
private fun createProgressiveMediaSource(url: String, metaData: MediaMetadata?): ProgressiveMediaSource {
    val factory = DefaultHttpDataSource.Factory()
    return ProgressiveMediaSource
        .Factory(factory)
        .createMediaSource(
            createMediaItem(url, metaData)
        )
}

@OptIn(UnstableApi::class)
private fun createHlsMediaSource(url: String, metaData: MediaMetadata?): HlsMediaSource {
    val factory = DefaultHttpDataSource.Factory()
    return HlsMediaSource
        .Factory(factory)
        .createMediaSource(
            createMediaItem(url, metaData)
        )
}

@OptIn(UnstableApi::class)
private fun createLocalFileMediaSource(url: String, metaData: MediaMetadata?): ProgressiveMediaSource {
    val factory = FileDataSource.Factory()
    return ProgressiveMediaSource
        .Factory(factory)
        .createMediaSource(createMediaItem(url, metaData))
}

private fun createMediaItem(url: String, metaData: MediaMetadata?): MediaItem {
    return MediaItem
        .Builder()
        .setUri(Uri.parse(url))
        .setMediaId(url)
        .applyIf(metaData != null) { setMediaMetadata(metaData!!) }
        .build()
}