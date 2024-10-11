package com.metacto.core.presentation.components.videoPlayer

import android.view.SurfaceView
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.metacto.core.presentation.components.visibilities.FadeVisibility
import com.metacto.core.presentation.theme.CoreTheme.shapes
import com.metacto.core.presentation.theme.CoreTheme.spacings
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.metacto.core.utils.extensions.noRippleClickable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import org.koin.compose.koinInject

private const val CONTROLS_ANIM_DURATION = 150

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
    playIconRes: ImageResource,
    pauseIconRes: ImageResource,
    onPlayerCreated: ((VideoPlayerController) -> Unit)?
) {
    // Inject main stuff
    val playerManagers = koinInject<MutableMap<String, VideoPlayerManager>>()
    val playerManager = playerManagers.getOrPut(uniqueId) {
        VideoPlayerManager(uniqueId)
    }

    // isPlaying state
    var isPlaying by remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }

    // Prepare player icon
    val icon = if (isPlaying) pauseIconRes else playIconRes

    // icon visibility state
    var isPlayButtonVisible by remember { mutableStateOf(true) }

    // Observe changes to the player state
    LaunchedEffect(playerManager.exoPlayer) {
        playerManager.exoPlayer.addListener(object : Player.Listener {
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                isPlaying = playWhenReady

                if (isPlaying) {
                    isPlayButtonVisible = false
                }
            }
        })
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

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    isPlayButtonVisible = isPlayButtonVisible.not()
                },
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

        if (showControls.not()) {
            FadeVisibility(
                visible = isPlayButtonVisible,
                duration = CONTROLS_ANIM_DURATION,
                modifier = Modifier.align(Alignment.Center)
            ) {
                // Player control icon
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(58.dp)
                        .shadow(
                            elevation = spacings.paddingXSmall,
                            shape = shapes.circle
                        )
                        .noRippleClickable {
                            if (isPlaying) {
                                playerManager.pause()
                            } else {
                                playerManager.play()
                            }
                        }
                )
            }
        }
    }

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