package com.metacto.core.presentation.components.videoPlayer

import android.view.SurfaceView
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.metacto.core.domain.DiQualifiers
import com.metacto.core.presentation.components.visibilities.FadeVisibility
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.metacto.core.utils.extensions.noRippleClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

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
    enableMediaMetadata: Boolean,
    handleLifecyclePause: Boolean,
    controllerShowTimeoutMs: Int,
    controlsType: ControlsType,
    playIconRes: DrawableResource,
    pauseIconRes: DrawableResource,
    customControlsSize: Dp,
    customControlsElevation: Dp,
    customControlsShape: RoundedCornerShape,
    onPlayerCreated: ((VideoPlayerController) -> Unit)?,
    onDurationCaught: ((Duration) -> Unit)?
) {
    // Inject main stuff
    val playerManagers = koinInject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    val playerManager = playerManagers.getOrPut(uniqueId) {
        VideoPlayerManager(uniqueId)
    }

    // isPlaying state
    var isPlaying by remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }

    // Prepare player icon
    val icon = if (isPlaying) pauseIconRes else playIconRes

    // icon visibility state
    var isPlayButtonVisible by remember { mutableStateOf(true) }

    // Track video ended state
    var isVideoEnded by remember { mutableStateOf(false) }

    // Observe changes to the player state
    LaunchedEffect(playerManager.exoPlayer) {
        playerManager.exoPlayer.addListener(object : Player.Listener {
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                isPlaying = playWhenReady
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    isPlaying = false
                    isVideoEnded = true
                }

                if (state == Player.STATE_READY) {
                    val durationMs = playerManager.exoPlayer.duration
                    if (durationMs != C.TIME_UNSET) {
                        onDurationCaught?.invoke(
                            durationMs.toDuration(DurationUnit.MILLISECONDS)
                        )
                    }
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

    // Configure media meta data
    LaunchedEffect(enableMediaMetadata) {
        playerManager.setMediaMetadataEnabled(enableMediaMetadata)
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
                    useController = controlsType == ControlsType.NativeControls
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

        if (controlsType == ControlsType.CustomControls) {
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
                        .size(customControlsSize)
                        .shadow(
                            elevation = customControlsElevation,
                            shape = customControlsShape
                        )
                        .noRippleClickable {
                            if (isPlaying) {
                                playerManager.pause()
                            } else {
                                if (isVideoEnded) {
                                    // Restart the video
                                    playerManager.exoPlayer.seekTo(0)
                                    isVideoEnded = false
                                    isPlaying = true
                                }
                                playerManager.play()
                                isPlaying = true
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