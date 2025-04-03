package com.metacto.core.utils.extensions

import android.content.Context
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.AssetDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.metacto.strapikmm.util.applyIf

fun createMediaSource(
    context: Context,
    url: String,
    metaData: MediaMetadata? = null
): MediaSource {
    val normalizedUrl = url.normalizeAssetPath()
    return when {
        normalizedUrl.contains(".m3u8") -> createHlsMediaSource(normalizedUrl, metaData)
        normalizedUrl.isLocalFile() -> createLocalFileMediaSource(normalizedUrl, metaData)
        normalizedUrl.isAssetFile() -> createAssetMediaSource(context, normalizedUrl, metaData)
        else -> createProgressiveMediaSource(normalizedUrl, metaData)
    }
}

@OptIn(UnstableApi::class)
private fun createProgressiveMediaSource(
    url: String,
    metaData: MediaMetadata?
): ProgressiveMediaSource {
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

@OptIn(UnstableApi::class)
private fun createAssetMediaSource(
    context: Context,
    url: String,
    metaData: MediaMetadata?
): ProgressiveMediaSource {
    return ProgressiveMediaSource
        .Factory(AssetDataSourceFactory(context))
        .createMediaSource(createMediaItem(url, metaData))
}

private fun createMediaItem(url: String, metaData: MediaMetadata?): MediaItem {
    return MediaItem
        .Builder()
        .setUri(url.toUri())
        .setMediaId(url)
        .applyIf(metaData != null) { setMediaMetadata(metaData!!) }
        .build()
}

private fun String.normalizeAssetPath(): String {
    return if (this.startsWith("file:///android_asset/")) {
        this.replace("file:///android_asset/", "asset:///") // Convert to ExoPlayer format
    } else {
        this
    }
}

private fun String.isAssetFile(): Boolean {
    return this.startsWith("asset:///")
}

@UnstableApi
class AssetDataSourceFactory(private val context: Context) : DataSource.Factory {
    override fun createDataSource(): DataSource {
        return AssetDataSource(context)
    }
}