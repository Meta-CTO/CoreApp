package com.metacto.core.presentation.components.videoPlayer

import android.content.res.Configuration
import android.view.SurfaceView
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    enableVoice: Boolean,
    autoRepeat: Boolean,
    handleLifecyclePause: Boolean,
    controllerShowTimeoutMs: Int,
    controlsType: ControlsType,
    playIconRes: DrawableResource,
    pauseIconRes: DrawableResource,
    customControlsSize: Dp,
    customControlsElevation: Dp,
    customControlsShape: RoundedCornerShape,
    onPlayerCreated: ((VideoPlayerController) -> Unit)?,
    onDurationCaught: ((Duration) -> Unit)?,
) {
    // Inject main stuff
    val playerManagers =
        koinInject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    val playerManager = playerManagers.getOrPut(uniqueId) {
        VideoPlayerManager(uniqueId)
    }
    // Local state variables for UI and playback.
    var isPlaying by remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }
    val icon = if (isPlaying) pauseIconRes else playIconRes
    val isPlayButtonVisible by remember { mutableStateOf(true) }
    var isVideoEnded by remember { mutableStateOf(false) }
    var isFullScreen by remember { mutableStateOf(false) }


    LaunchedEffect(playerManager, enableVoice) {
        playerManager.exoPlayer.volume = if (enableVoice) 1f else 0f
    }

    // Listen for player state changes.
    LaunchedEffect(playerManager.exoPlayer) {
        playerManager.exoPlayer.addListener(object : Player.Listener {
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                isPlaying = playWhenReady
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    isVideoEnded = autoRepeat.not()
                }
                if (state == Player.STATE_READY) {
                    val durationMs = playerManager.exoPlayer.duration
                    if (durationMs != C.TIME_UNSET) {
                        onDurationCaught?.invoke(durationMs.toDuration(DurationUnit.MILLISECONDS))
                    }
                }
            }
        })
    }
    // Configure auto play, repeat, media, and metadata.
    LaunchedEffect(playerManager, autoPlay) {
        playerManager.setAutoPlay(autoPlay)
    }

    LaunchedEffect(playerManager, autoRepeat) {
        playerManager.setAutoRepeat(autoRepeat)
    }

    LaunchedEffect(playerManager, videoUrl, videoTitle, videoArtist, videoArtworkUrl) {
        playerManager.setMedia(
            videoUrl = videoUrl,
            videoTitle = videoTitle,
            videoArtist = videoArtist,
            videoArtworkUrl = videoArtworkUrl
        )
    }
    LaunchedEffect(enableMediaMetadata) {
        playerManager.setMediaMetadataEnabled(enableMediaMetadata)
    }
    // Set up and deliver the player controller callback.
    val controller = remember(playerManager) {
        object : VideoPlayerController {
            override fun play() = playerManager.play()
            override fun pause() = playerManager.pause()
        }
    }
    LaunchedEffect(controller, onPlayerCreated) {
        onPlayerCreated?.invoke(controller)
    }
    // Monitor device orientation to update full screen & scale mode.
    val configuration = LocalConfiguration.current
    LaunchedEffect(configuration.orientation) {
        isFullScreen = (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
    // Render the video content (either embedded or full screen).
    if (!isFullScreen) {
        Box(modifier = modifier) {
            VideoPlayerContent(
                playerManager = playerManager,
                controlsType = controlsType,
                controllerShowTimeoutMs = controllerShowTimeoutMs,
                isPlayButtonVisible = isPlayButtonVisible,
                icon = icon,
                customControlsSize = customControlsSize,
                customControlsElevation = customControlsElevation,
                customControlsShape = customControlsShape,
                onTogglePlay = {
                    if (isPlaying) {
                        playerManager.pause()
                    } else {
                        if (isVideoEnded) {
                            playerManager.exoPlayer.seekTo(0)
                            isVideoEnded = false
                        }
                        playerManager.play()
                        isPlaying = true
                    }
                },
                onNativeFullscreenClick = {
                    isFullScreen = !isFullScreen
                    // Optionally toggle scale mode with full screen.
                }
            )
        }
    } else {
        Dialog(
            onDismissRequest = { isFullScreen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
					.fillMaxSize()
					.background(Color.Black)
            ) {
                VideoPlayerContent(
                    playerManager = playerManager,
                    controlsType = controlsType,
                    controllerShowTimeoutMs = controllerShowTimeoutMs,
                    isPlayButtonVisible = isPlayButtonVisible,
                    icon = icon,
                    customControlsSize = customControlsSize,
                    customControlsElevation = customControlsElevation,
                    customControlsShape = customControlsShape,
                    onTogglePlay = {
                        if (isPlaying) {
                            playerManager.pause()
                        } else {
                            if (isVideoEnded) {
                                playerManager.exoPlayer.seekTo(0)
                                isVideoEnded = false
                            }
                            playerManager.play()
                            isPlaying = true
                        }
                    },
                    onNativeFullscreenClick = {
                        isFullScreen = !isFullScreen
                    }
                )
            }
        }
    }
    // Pause the player when the composable is disposed or when the lifecycle pauses.
    DisposableEffect(Unit) {
        onDispose {
            if (handleLifecyclePause) {
                playerManager.pause()
            }
        }
    }
    OnLifecycleEvent(
        onPause = {
            if (handleLifecyclePause) {
                playerManager.pause()
            }
        }
    )
}

// Video player content composable.
@OptIn(UnstableApi::class)
@Composable
private fun VideoPlayerContent(
    playerManager: VideoPlayerManager,
    controlsType: ControlsType,
    controllerShowTimeoutMs: Int,
    isPlayButtonVisible: Boolean,
    icon: DrawableResource,
    customControlsSize: Dp,
    customControlsElevation: Dp,
    customControlsShape: RoundedCornerShape,
    onTogglePlay: () -> Unit,
    onNativeFullscreenClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    useController = (controlsType == ControlsType.NativeControls)

                    this.controllerShowTimeoutMs = controllerShowTimeoutMs

                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH

                    player = playerManager.exoPlayer

                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                    (videoSurfaceView as? SurfaceView)?.let {
                        playerManager.exoPlayer.setVideoSurfaceView(it)
                    }
                    if (controlsType == ControlsType.NativeControls) {
                        // Leverage native full screen button.
                        setFullscreenButtonClickListener { onNativeFullscreenClick() }
                    }
                }
            },
            update = { playerView ->
                (playerView.videoSurfaceView as? SurfaceView)?.let {
                    playerManager.exoPlayer.setVideoSurfaceView(it)
                }
            }
        )
        // For custom controls, overlay a play/pause button.
        if (controlsType == ControlsType.CustomControls) {
            FadeVisibility(
                visible = isPlayButtonVisible,
                duration = CONTROLS_ANIM_DURATION,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
						.size(customControlsSize)
						.shadow(
							elevation = customControlsElevation,
							shape = customControlsShape
						)
						.noRippleClickable { onTogglePlay() }
                )
            }
        }
    }
}
