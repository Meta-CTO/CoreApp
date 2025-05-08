package com.metacto.core.presentation.components.videoPlayer

import android.util.TypedValue
import android.view.SurfaceView
import android.view.View
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

    // State management
    val isPlaying = remember { mutableStateOf(playerManager.exoPlayer.isPlaying) }
    val isVideoEnded = remember { mutableStateOf(false) }
    var enableRendering by remember { mutableStateOf(true) } // Default to true for composable player
    var shouldResumePlayback by remember { mutableStateOf(false) }
    var surfaceRecreationTrigger by remember { mutableStateOf(false) }
    val icon = if (isPlaying.value) pauseIconRes else playIconRes
    val isPlayButtonVisible by remember { mutableStateOf(true) }
    val playerViewRef = remember { mutableStateOf<PlayerView?>(null) }

    val isCasting by playerManager.isCasting.collectAsState()

    val subtitleFilePicker = rememberSubtitleFilePicker { language, fileName, content ->
        playerManager.addExternalSubtitle(language, fileName, content)
    }

    // Listen for Activity finishing to re-enable rendering in the composable
    eventBroadcaster.collectInCompose<VideoPlayerEvent.ActivityFinished> {
        if (it.playerId == uniqueId) {
            enableRendering = true
            if (shouldResumePlayback && playerManager.exoPlayer.playbackState != Player.STATE_ENDED) {
                playerManager.play()
                shouldResumePlayback = false
            }
            isPlaying.value = playerManager.exoPlayer.isPlaying
            playerViewRef.value?.subtitleView?.visibility = View.VISIBLE
            surfaceRecreationTrigger = !surfaceRecreationTrigger
        }
    }

    // Listen for PiP start to disable rendering in the composable
    eventBroadcaster.collectInCompose<VideoPlayerEvent.StartedPip> {
        if (it.playerId == uniqueId) {
            shouldResumePlayback = playerManager.exoPlayer.isPlaying
            enableRendering = false
            playerViewRef.value?.subtitleView?.visibility = View.INVISIBLE
            surfaceRecreationTrigger = !surfaceRecreationTrigger
        }
    }

    // Listen for PiP stop
    eventBroadcaster.collectInCompose<VideoPlayerEvent.StoppedPip> {
        if (it.playerId == uniqueId) {
            if (eventBroadcaster.getPipActivePlayerId() == null) {
                enableRendering = true
                if (shouldResumePlayback && playerManager.exoPlayer.playbackState != Player.STATE_ENDED) {
                    playerManager.play()
                    shouldResumePlayback = false
                }
                isPlaying.value = playerManager.exoPlayer.isPlaying
                playerViewRef.value?.subtitleView?.visibility = View.VISIBLE
                surfaceRecreationTrigger = !surfaceRecreationTrigger
            }
        }
    }

    LaunchedEffect(enablePip) {
        playerManager.isPipEnabled = enablePip
    }

    val controller = remember(playerManager) {
        object : VideoPlayerController {
            override fun play() {
                playerManager.play()
                eventBroadcaster.emit(VideoPlayerEvent.PlayerStarted(uniqueId))
            }

            override fun pause() = playerManager.pause()
        }
    }

    // Player listener setup
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

    // Initialize player with media info
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
        surfaceRecreationTrigger = !surfaceRecreationTrigger
        VideoPlayerActivity.start(context = context, uniqueId = id, enablePip = enablePip)
    }

    Box(modifier = modifier) {
        // Video player view
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = (controlsType == ControlsType.NativeControls)
                    this.controllerShowTimeoutMs = controllerShowTimeoutMs
                    this.resizeMode = if (scaleToCrop) AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    else AspectRatioFrameLayout.RESIZE_MODE_FIT
                    subtitleView?.setFixedTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    subtitleView?.setPaddingRelative(0, 0, 0, 20)
                    subtitleView?.setApplyEmbeddedStyles(true)
                    subtitleView?.setCues(null)

                    setFullscreenButtonClickListener {
                        onFullScreen(uniqueId)
                    }
                }.also {
                    playerViewRef.value = it
                }
            },
            update = { view ->
                playerViewRef.value = view
                if (enableRendering) {
                    if (view.player != playerManager.exoPlayer) {
                        view.player = null
                        view.player = playerManager.exoPlayer
                    } else {
                        (view.videoSurfaceView as? SurfaceView)?.let {
                            playerManager.exoPlayer.setVideoSurfaceView(it)
                        }
                    }
                    view.subtitleView?.visibility = View.VISIBLE
                    if (playerManager.exoPlayer.playWhenReady && !playerManager.exoPlayer.isPlaying && playerManager.exoPlayer.playbackState != Player.STATE_ENDED) {
                        playerManager.exoPlayer.play()
                    }
                } else {
                    if (view.player != null) {
                        view.player = null
                    }
                    view.subtitleView?.visibility = View.INVISIBLE
                }
            }
        )

        // Custom play/pause control
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

        // Top row controls (subtitle and cast buttons)
        FadeVisibility(
            visible = isPlayButtonVisible && enableRendering,
            duration = CONTROLS_ANIM_DURATION,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
        ) {
            Row {
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
                        .background(color = Color.LightGray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    AndroidView(
                        factory = { ctx ->
                            MediaRouteButton(ctx).apply {
                                playerManager.setupCastButton(this)
                            }
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Casting indicator
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

    // Cleanup when component is removed
    DisposableEffect(Unit) {
        onDispose { }
    }

    // Handle app lifecycle events
    OnLifecycleEvent(
        onPause = {
            if (handleLifecyclePause && enableRendering) {
                if (!playerManager.exoPlayer.isPlaying && shouldResumePlayback) {
                } else {
                    shouldResumePlayback = playerManager.exoPlayer.isPlaying
                }
                playerManager.pause()
            }
        },
        onResume = {
            if (handleLifecyclePause && enableRendering && shouldResumePlayback) {
                if (playerManager.exoPlayer.playbackState != Player.STATE_ENDED) {
                    playerManager.play()
                }
                shouldResumePlayback = false
            }
        }
    )
}

// Utility function for toggling play/pause
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