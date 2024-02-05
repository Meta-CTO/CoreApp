package com.metacto.core.presentation.components.videoPlayer

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.metacto.core.utils.extensions.OnLifecycleEvent

@OptIn(UnstableApi::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    autoPlay: Boolean,
    scaleToCrop: Boolean,
    url: String
) {
    val context = LocalContext.current

    val exoPlayer = remember(url) {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(url))

                setMediaSource(source)
                prepare()
            }
    }

    if (autoPlay) {
        exoPlayer.playWhenReady = true
    }
    if (scaleToCrop) {
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    } else {
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                useController = true
                controllerShowTimeoutMs = 0
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Handle lifecycle
    OnLifecycleEvent(
        onPause = {
            exoPlayer.pause()
        },
        onDispose = {
            exoPlayer.pause()
            exoPlayer.release()
        }
    )
}