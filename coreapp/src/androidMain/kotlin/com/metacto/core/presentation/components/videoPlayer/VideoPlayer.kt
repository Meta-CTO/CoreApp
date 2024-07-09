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
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.metacto.core.utils.extensions.kill
import com.metacto.core.utils.extensions.setMediaSource

@OptIn(UnstableApi::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    autoPlay: Boolean,
    scaleToCrop: Boolean,
    url: String
) {
    val context = LocalContext.current

    val exoPlayer = remember(url, autoPlay, scaleToCrop) {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                // Configure the player
                playWhenReady = autoPlay
                videoScalingMode = when (scaleToCrop) {
                    true -> C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                    false -> C.VIDEO_SCALING_MODE_DEFAULT
                }

                // Then load media
                setMediaSource(url)
                prepare()
            }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                useController = true
                controllerShowTimeoutMs = 0
                resizeMode = when(scaleToCrop) {
                    true -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                    false -> AspectRatioFrameLayout.RESIZE_MODE_FIT
                }

                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        },
        update = {
            if (it.player != exoPlayer) {
                it.player?.kill()
                it.player = exoPlayer
            }
        },
        onRelease = {
            exoPlayer.kill()
            it.player?.kill()
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
            exoPlayer.kill()
        }
    )
}