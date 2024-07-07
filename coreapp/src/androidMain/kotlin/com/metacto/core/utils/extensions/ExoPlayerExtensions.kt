package com.metacto.core.utils.extensions

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

fun Player.kill() {
    stop()
    release()
}

@OptIn(UnstableApi::class)
fun ExoPlayer.setMediaSource(url: String) {
    val mediaSource = when {
        url.contains(".m3u8") -> createHlsMediaSource(url)
        else -> createProgressiveMediaSource(url)
    }

    setMediaSource(mediaSource)
}

@OptIn(UnstableApi::class)
private fun createProgressiveMediaSource(url: String): ProgressiveMediaSource {
    val factory = DefaultHttpDataSource.Factory()
    return ProgressiveMediaSource
        .Factory(factory)
        .createMediaSource(MediaItem.fromUri(url))
}

@OptIn(UnstableApi::class)
private fun createHlsMediaSource(url: String): HlsMediaSource {
    val factory = DefaultHttpDataSource.Factory()
    return HlsMediaSource
        .Factory(factory)
        .createMediaSource(MediaItem.fromUri(url))
}