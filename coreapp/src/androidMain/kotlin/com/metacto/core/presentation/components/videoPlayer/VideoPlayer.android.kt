package com.metacto.core.presentation.components.videoPlayer

import android.util.TypedValue
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.mediarouter.app.MediaRouteButton
import com.metacto.core.domain.DiQualifiers
import com.metacto.core.presentation.components.visibilities.FadeVisibility
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.metacto.core.utils.extensions.noRippleClickable
import com.metacto.coreApp.R
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
    onVideoEnd: (() -> Unit)?,
) {
    val context = LocalContext.current
    val eventBroadcaster = koinInject<VideoPlayerEventBroadcaster>()
    val playerManagers =
        koinInject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    val playerManager = playerManagers.getOrPut(uniqueId) { VideoPlayerManager(uniqueId) }

    val isPlaying = remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }
    val isVideoEnded = remember { mutableStateOf(false) }
    var enableRendering by remember { mutableStateOf(true) }
    var shouldResumePlayback by remember { mutableStateOf(false) }
    val icon = if (isPlaying.value) pauseIconRes else playIconRes
    val isPlayButtonVisible by remember { mutableStateOf(true) }
    val playerViewRef = remember { mutableStateOf<PlayerView?>(null) }

    // Cast support
    val isCasting by playerManager.isCasting.collectAsState()

    // Subtitle file loading
    val subtitleFilePicker = rememberSubtitleFilePicker { language, fileName, content ->
        playerManager.addExternalSubtitle(language, fileName, content)
    }

    eventBroadcaster.collectInCompose<VideoPlayerEvent.ActivityFinished> {
        enableRendering = true
        if (shouldResumePlayback) {
            playerManager.play()
            shouldResumePlayback = false
        }
        isPlaying.value = playerManager.exoPlayer.isPlaying

        // Ensure subtitles are visible when activity finishes
        playerViewRef.value?.subtitleView?.visibility = View.VISIBLE
    }

    eventBroadcaster.collectInCompose<VideoPlayerEvent.StartedPip> {
        enableRendering = false
        shouldResumePlayback = playerManager.exoPlayer.isPlaying

        // Hide subtitles when entering PIP mode
        playerViewRef.value?.subtitleView?.visibility = View.INVISIBLE
    }

    eventBroadcaster.collectInCompose<VideoPlayerEvent.StoppedPip> {
        enableRendering = false
        shouldResumePlayback = playerManager.exoPlayer.isPlaying

        // Show subtitles when exiting PIP mode
        playerViewRef.value?.subtitleView?.visibility = View.VISIBLE
    }

    LaunchedEffect(enablePip) {
        playerManager.isPipEnabled = enablePip
    }

    val controller = remember(playerManager) {
        object : VideoPlayerController {
            override fun play() = playerManager.play()
            override fun pause() = playerManager.pause()
        }
    }

    LaunchedEffect(key1 = playerManager) {
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

    LaunchedEffect(
        playerManager, videoUrl, videoTitle, videoArtist, videoArtworkUrl,
        autoPlay, scaleToCrop, autoRepeat, enableVoice, enableMediaMetadata,
        onVideoLoop, onVideoEnd, controller, onPlayerCreated
    ) {
        playerManager.setMedia(
            videoUrl = videoUrl,
            videoTitle = videoTitle,
            videoArtist = videoArtist,
            videoArtworkUrl = videoArtworkUrl
        )
        playerManager.setAutoPlay(autoPlay)
        playerManager.setScaleToCrop(scaleToCrop)
        playerManager.setAutoRepeat(autoRepeat)
        playerManager.setMediaMetadataEnabled(enableMediaMetadata)
        playerManager.exoPlayer.volume = if (enableVoice) 1f else 0f
        playerManager.onVideoLoop = onVideoLoop
        playerManager.onVideoEnd = onVideoEnd
        onPlayerCreated?.invoke(controller)
    }

    fun onFullScreen(id: String) {
        shouldResumePlayback = playerManager.exoPlayer.isPlaying
        enableRendering = false
        VideoPlayerActivity.start(context = context, uniqueId = id, enablePip = enablePip)
    }

    Box(modifier = modifier) {
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
                ).also {
                    playerViewRef.value = it
                }
            },
            update = { playerView ->
                playerViewRef.value = playerView
                updatePlayerView(
                    playerView = playerView,
                    playerManager = playerManager,
                    enableRendering = enableRendering,
                    onFullscreenClick = { onFullScreen(uniqueId) }
                )
            }
        )

        // Custom Controls - Play/Pause button
        if (controlsType == ControlsType.CustomControls) {
            FadeVisibility(
                visible = isPlayButtonVisible && enableRendering,
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

        // Top control bar with subtitle and cast buttons
        FadeVisibility(
            visible = isPlayButtonVisible && enableRendering,
            duration = CONTROLS_ANIM_DURATION,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
        ) {
            Row {
                // Subtitle button - directly opens file picker
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.LightGray, shape = CircleShape)
                        .clickable { subtitleFilePicker() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ClosedCaption,
                        contentDescription = "Load Subtitle File",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.LightGray, shape = CircleShape)
                        .clickable { subtitleFilePicker() },
                    contentAlignment = Alignment.Center
                ) {
                    // Cast button
                    AndroidView(
                        factory = { context ->
                            MediaRouteButton(context).apply {
                                playerManager.setupCastButton(this)
                            }
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Casting Indicator
        if (isCasting) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.casting_to_device),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
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

    OnLifecycleEvent(
        onPause = {
            if (handleLifecyclePause && enableRendering) {
                shouldResumePlayback = playerManager.exoPlayer.isPlaying
                playerManager.pause()
            }
        },
        onResume = {
            if (handleLifecyclePause && enableRendering && shouldResumePlayback) {
                playerManager.play()
                shouldResumePlayback = false
            }
        }
    )
}

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

        subtitleView?.setFixedTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        subtitleView?.setPaddingRelative(0, 0, 0, 20)
        subtitleView?.setApplyEmbeddedStyles(true)
        subtitleView?.setCues(null)

        setFullscreenButtonClickListener {
            onFullscreenClick()
        }

        if (enableRendering) {
            (videoSurfaceView as? SurfaceView)?.let {
                playerManager.exoPlayer.setVideoSurfaceView(it)
            } ?: run {
                playerManager.exoPlayer.setVideoSurface(null)
                playerManager.exoPlayer.setVideoSurfaceView(videoSurfaceView as? SurfaceView)
            }
        }
    }
}

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

    playerView.subtitleView?.setFixedTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
    playerView.subtitleView?.setPaddingRelative(0, 0, 0, 20)

    if (enableRendering) {
        if (playerView.player != playerManager.exoPlayer) {
            playerView.player = playerManager.exoPlayer
        }
        (playerView.videoSurfaceView as? SurfaceView)?.let {
            playerManager.exoPlayer.setVideoSurfaceView(it)
        } ?: run {
            playerManager.exoPlayer.setVideoSurface(null)
            playerManager.exoPlayer.setVideoSurfaceView(playerView.videoSurfaceView as? SurfaceView)
        }
    } else {
        playerManager.exoPlayer.clearVideoSurface()
    }
}