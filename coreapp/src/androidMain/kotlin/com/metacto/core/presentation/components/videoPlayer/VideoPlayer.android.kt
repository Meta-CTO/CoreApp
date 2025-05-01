package com.metacto.core.presentation.components.videoPlayer

import android.annotation.SuppressLint
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
    // Inject main stuff
    val context = LocalContext.current
    val eventBroadcaster = koinInject<VideoPlayerEventBroadcaster>()
    val playerManagers =
        koinInject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    val playerManager = playerManagers.getOrPut(uniqueId) {
        VideoPlayerManager(uniqueId)
    }

    // Local state variables for UI and playback.
    val isPlaying = remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }
    val icon = if (isPlaying.value) pauseIconRes else playIconRes
    val isPlayButtonVisible by remember { mutableStateOf(true) }
    val isVideoEnded = remember { mutableStateOf(false) }

    // TODO: should change full screen icon to fixed icon
    // Full screen handler
    var enableRendering by remember {
        mutableStateOf(true)
    }
    fun onFullScreen(uniqueId: String) {
        VideoPlayerActivity.start(
            context = context,
            uniqueId = uniqueId
        )
        enableRendering = false
    }

    // Collect required events
    eventBroadcaster.collectInCompose<VideoPlayerEvent.StoppedPip> {
        enableRendering = true
    }

    // Listen for player state changes.
    LaunchedEffect(playerManager.exoPlayer) {
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

    // Voice configuration
    LaunchedEffect(playerManager, enableVoice) {
        playerManager.exoPlayer.volume = if (enableVoice) 1f else 0f
    }

    // Setup scaling mode
    LaunchedEffect(playerManager, scaleToCrop) {
        playerManager.setScaleToCrop(scaleToCrop)
    }

    // Auto play configuration
    LaunchedEffect(playerManager, autoPlay) {
        playerManager.setAutoPlay(autoPlay)
    }

    // Auto repeat configuration
    LaunchedEffect(playerManager, autoRepeat) {
        playerManager.setAutoRepeat(autoRepeat)
    }

    // Media metadata configuration
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

    // Video loop configuration
    LaunchedEffect(onVideoLoop) {
        playerManager.onVideoLoop = onVideoLoop
    }

    // Video end configuration
    LaunchedEffect(onVideoEnd) {
        playerManager.onVideoEnd = onVideoEnd
    }

    // Render normal video player if needed
    NormalVideoPlayer(
        modifier = modifier,
        playerManager = playerManager,
        enableRendering = enableRendering,
        isPlayButtonVisible = isPlayButtonVisible,
        icon = icon,
        isPlaying = isPlaying,
        isVideoEnded = isVideoEnded,
        scaleToCrop = scaleToCrop,
        controllerShowTimeoutMs = controllerShowTimeoutMs,
        controlsType = controlsType,
        customControlsSize = customControlsSize,
        customControlsElevation = customControlsElevation,
        customControlsShape = customControlsShape,
        onFullscreenClick = {
            onFullScreen(uniqueId)
        }
    )

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

@SuppressLint("UnsafeOptInUsageError")
@Composable
private fun NormalVideoPlayer(
    modifier: Modifier,
    playerManager: VideoPlayerManager,
    enableRendering: Boolean,
    isPlayButtonVisible: Boolean,
    icon: DrawableResource,
    isPlaying: MutableState<Boolean>,
    isVideoEnded: MutableState<Boolean>,
    onFullscreenClick: () -> Unit,
    scaleToCrop: Boolean,
    controllerShowTimeoutMs: Int,
    controlsType: ControlsType,
    customControlsSize: Dp,
    customControlsElevation: Dp,
    customControlsShape: RoundedCornerShape
) {
    Box(
        modifier = modifier
    ) {
        VideoPlayerContent(
            playerManager = playerManager,
            enableRendering = enableRendering,
            controlsType = controlsType,
            controllerShowTimeoutMs = controllerShowTimeoutMs,
            isPlayButtonVisible = isPlayButtonVisible,
            icon = icon,
            customControlsSize = customControlsSize,
            customControlsElevation = customControlsElevation,
            customControlsShape = customControlsShape,
            resizeMode = when (scaleToCrop) {
                true -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                false -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            },
            onNativeFullscreenClick = onFullscreenClick,
            onTogglePlay = {
                if (isPlaying.value) {
                    playerManager.pause()
                } else {
                    if (isVideoEnded.value) {
                        playerManager.exoPlayer.seekTo(0)
                        isVideoEnded.value = false
                    }
                    playerManager.play()
                    isPlaying.value = true
                }
            }
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoPlayerContent(
    playerManager: VideoPlayerManager,
    enableRendering: Boolean,
    controlsType: ControlsType,
    controllerShowTimeoutMs: Int,
    resizeMode: Int,
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
                    this.resizeMode = resizeMode
                    player = playerManager.exoPlayer
                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                    setFullscreenButtonClickListener {
                        onNativeFullscreenClick()
                    }

                    if (enableRendering) {
                        (videoSurfaceView as? SurfaceView)?.let {
                            playerManager.exoPlayer.setVideoSurfaceView(it)
                        }
                    }
                }
            },
            update = { playerView ->
                playerView.setFullscreenButtonClickListener {
                    onNativeFullscreenClick()
                }

                if (enableRendering) {
                    (playerView.videoSurfaceView as? SurfaceView)?.let {
                        playerManager.exoPlayer.setVideoSurfaceView(it)
                    }
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