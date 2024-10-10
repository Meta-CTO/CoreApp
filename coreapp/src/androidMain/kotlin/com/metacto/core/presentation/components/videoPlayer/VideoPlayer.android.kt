package com.metacto.core.presentation.components.videoPlayer

import android.view.SurfaceView
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.metacto.core.utils.extensions.OnLifecycleEvent
import org.koin.compose.koinInject

@OptIn(UnstableApi::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    uniqueId: String,
    videoUrl: String,
    videoArtist: String?,
    videoTitle: String?,
    videoArtworkUrl: String?,
    autoPlay: Boolean,
    scaleToCrop: Boolean,
    enablePip: Boolean,
    handleLifecyclePause: Boolean,
    controllerShowTimeoutMs: Int,
    showControls: Boolean,
    onPlayerCreated: ((VideoPlayerController) -> Unit)?
) {
    // Inject main stuff
    val playerManagers = koinInject<MutableMap<String, VideoPlayerManager>>()
    val playerManager = playerManagers.getOrPut(uniqueId) {
        VideoPlayerManager(uniqueId)
    }

    // Setup scaling mode
    LaunchedEffect(playerManager, scaleToCrop) {
        playerManager.setScaleToCrop(scaleToCrop)
    }

    // Setup auto play
    LaunchedEffect(playerManager, autoPlay) {
        playerManager.setAutoPlay(autoPlay)
    }

    // Configure the player
    LaunchedEffect(playerManager, videoUrl, videoTitle, videoArtist, videoArtworkUrl) {
        playerManager.setMedia(
            videoUrl = videoUrl,
            videoTitle = videoTitle,
            videoArtist = videoArtist,
            videoArtworkUrl = videoArtworkUrl
        )
    }

    // Create the player controller
    val playerController = remember(playerManager) {
        object : VideoPlayerController {
            override fun play() {
                playerManager.play()
            }

            override fun pause() {
                playerManager.pause()
            }
        }
    }

    // Launched effect to invoke player created
    LaunchedEffect(playerController, onPlayerCreated) {
        onPlayerCreated?.invoke(playerController)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            PlayerView(context).apply {
                useController = showControls
                this.controllerShowTimeoutMs = controllerShowTimeoutMs
                resizeMode = when (scaleToCrop) {
                    true -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                    false -> AspectRatioFrameLayout.RESIZE_MODE_FIT
                }

                player = playerManager.exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                (videoSurfaceView as? SurfaceView)?.let {
                    playerManager.exoPlayer.setVideoSurfaceView(it)
                }
            }
        },
        update = { playerView ->
            (playerView.videoSurfaceView as? SurfaceView)?.let {
                playerManager.exoPlayer.setVideoSurfaceView(it)
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            if (handleLifecyclePause) {
                playerManager.pause()
            }
        }
    }

    // Handle lifecycle
    OnLifecycleEvent(
        onPause = {
            if (handleLifecyclePause) {
                playerManager.pause()
            }
        }
    )
}