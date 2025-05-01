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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.launch
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
    onVideoLoop: (() -> Unit)?,
    onVideoEnd: (() -> Unit)?
) {
    // Setup context and dependencies
    val context = LocalContext.current
    val eventBroadcaster = koinInject<VideoPlayerEventBroadcaster>()
    val playerManagers =
        koinInject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    val playerManager = playerManagers.getOrPut(uniqueId) { VideoPlayerManager(uniqueId) }

    // State management
    val isPlaying = remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }
    val isVideoEnded = remember { mutableStateOf(false) }
    var enableRendering by remember { mutableStateOf(true) }
    val icon = if (isPlaying.value) pauseIconRes else playIconRes
    val isPlayButtonVisible by remember { mutableStateOf(true) }

    // Event handling
    eventBroadcaster.collectInCompose<VideoPlayerEvent.StoppedPip> {
        enableRendering = true
    }

    eventBroadcaster.collectInCompose<VideoPlayerEvent.ReturnedFromFullscreen> { event ->
        enableRendering = true
        if (event.wasPlaying) {
            // Resume playback
            playerManager.play()
        }
    }

    // Player controller setup
    val controller = remember(playerManager) {
        object : VideoPlayerController {
            override fun play() = playerManager.play()
            override fun pause() = playerManager.pause()
        }
    }

    // Configure player options
    LaunchedEffect(key1 = playerManager) {
        // Setup player listeners
        playerManager.exoPlayer.addListener(object : Player.Listener {
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                isPlaying.value = playWhenReady
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    isVideoEnded.value = autoRepeat.not()
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

    // Configure player settings
    LaunchedEffect(
        playerManager, videoUrl, videoTitle, videoArtist, videoArtworkUrl,
        autoPlay, scaleToCrop, autoRepeat, enableVoice, enableMediaMetadata,
        onVideoLoop, onVideoEnd, controller, onPlayerCreated
    ) {
        // Media configuration
        playerManager.setMedia(
            videoUrl = videoUrl,
            videoTitle = videoTitle,
            videoArtist = videoArtist,
            videoArtworkUrl = videoArtworkUrl
        )

        // Player settings
        playerManager.setAutoPlay(autoPlay)
        playerManager.setScaleToCrop(scaleToCrop)
        playerManager.setAutoRepeat(autoRepeat)
        playerManager.setMediaMetadataEnabled(enableMediaMetadata)
        playerManager.exoPlayer.volume = if (enableVoice) 1f else 0f

        // Callbacks
        playerManager.onVideoLoop = onVideoLoop
        playerManager.onVideoEnd = onVideoEnd
        onPlayerCreated?.invoke(controller)
    }

    // Fullscreen handler
    fun onFullScreen(id: String) {
        VideoPlayerActivity.start(context = context, uniqueId = id)
        enableRendering = false
    }

    // Render video player
    Box(modifier = modifier) {
        // Player view
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                createPlayerView(
                    context = ctx,
                    playerManager = playerManager,
                    controlsType = controlsType,
                    controllerShowTimeoutMs = controllerShowTimeoutMs,
                    resizeMode = if (scaleToCrop) AspectRatioFrameLayout.RESIZE_MODE_FILL
                    else AspectRatioFrameLayout.RESIZE_MODE_FIT,
                    enableRendering = enableRendering,
                    onFullscreenClick = { onFullScreen(uniqueId) }
                )
            },
            update = { playerView ->
                updatePlayerView(
                    playerView = playerView,
                    playerManager = playerManager,
                    enableRendering = enableRendering,
                    onFullscreenClick = { onFullScreen(uniqueId) }
                )
            }
        )

        // Custom controls overlay
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
                        .noRippleClickable {
                            togglePlayback(
                                isPlaying = isPlaying.value,
                                isVideoEnded = isVideoEnded,
                                playerManager = playerManager
                            )
                        }
                )
            }
        }
    }

    // Lifecycle management
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


// Toggles playback between play and pause states

@OptIn(UnstableApi::class)
private fun togglePlayback(
    isPlaying: Boolean,
    isVideoEnded: MutableState<Boolean>,
    playerManager: VideoPlayerManager
) {
    if (isPlaying) {
        playerManager.pause()
    } else {
        if (isVideoEnded.value) {
            playerManager.exoPlayer.seekTo(0)
            isVideoEnded.value = false
        }
        playerManager.play()
    }
}

// Creates a PlayerView with the specified configuration
@OptIn(UnstableApi::class)
private fun createPlayerView(
    context: android.content.Context,
    playerManager: VideoPlayerManager,
    controlsType: ControlsType,
    controllerShowTimeoutMs: Int,
    resizeMode: Int,
    enableRendering: Boolean,
    onFullscreenClick: () -> Unit
): PlayerView {
    return PlayerView(context).apply {
        useController = (controlsType == ControlsType.NativeControls)
        this.controllerShowTimeoutMs = controllerShowTimeoutMs
        this.resizeMode = resizeMode
        player = playerManager.exoPlayer
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setFullscreenButtonClickListener {
            onFullscreenClick()
        }

        if (enableRendering) {
            (videoSurfaceView as? SurfaceView)?.let {
                playerManager.exoPlayer.setVideoSurfaceView(it)
            }
        }
    }
}

// Updates an existing PlayerView with new configuration
@OptIn(UnstableApi::class)
private fun updatePlayerView(
    playerView: PlayerView,
    playerManager: VideoPlayerManager,
    enableRendering: Boolean,
    onFullscreenClick: () -> Unit
) {
    playerView.setFullscreenButtonClickListener {
        onFullscreenClick()
    }

    if (enableRendering) {
        (playerView.videoSurfaceView as? SurfaceView)?.let {
            playerManager.exoPlayer.setVideoSurfaceView(it)
        }
    }
}