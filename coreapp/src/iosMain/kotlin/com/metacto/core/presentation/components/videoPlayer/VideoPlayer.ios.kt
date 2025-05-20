package com.metacto.core.presentation.components.videoPlayer

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.metacto.core.presentation.components.visibilities.FadeVisibility
import com.metacto.core.utils.extensions.DefaultLaunchedEffect
import com.metacto.core.utils.extensions.IOLaunchedEffect
import com.metacto.core.utils.extensions.cleanFilePath
import com.metacto.core.utils.extensions.isValidUrl
import com.metacto.core.utils.extensions.noRippleClickable
import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.AVAudioSessionModeMoviePlayback
import platform.AVFAudio.setActive
import platform.AVFoundation.AVLayerVideoGravityResizeAspect
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMetadataCommonIdentifierArtist
import platform.AVFoundation.AVMetadataCommonIdentifierArtwork
import platform.AVFoundation.AVMetadataCommonIdentifierTitle
import platform.AVFoundation.AVMetadataKeySpaceCommon
import platform.AVFoundation.AVMutableMetadataItem
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.asset
import platform.AVFoundation.currentItem
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.rate
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.seekToTime
import platform.AVFoundation.setKeySpace
import platform.AVFoundation.volume
import platform.AVKit.AVPictureInPictureController
import platform.AVKit.AVPlayerViewController
import platform.AVKit.externalMetadata
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSData
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIView
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val CONTROLS_ANIM_DURATION = 150

@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
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
    onVideoStarted: (() -> Unit)?,
    onVideoPaused: (() -> Unit)?,
    onVideoResumed: (() -> Unit)?
) {
    // Create the player item with the url
    val playerItem = remember(videoUrl) {
        // Check if the url is empty of a valid url so we will consider this is a url
        // (Checking for empty is mandatory as it will crash if we tried to handle the empty url as a file)
        val nsUrl = if (videoUrl.isEmpty() || videoUrl.isValidUrl()) {
            NSURL.URLWithString(videoUrl)!!
        } else {
            NSURL.fileURLWithPath(videoUrl.cleanFilePath())
        }
        AVPlayerItem(uRL = nsUrl)
    }

    // Set the title metadata
    LaunchedEffect(playerItem, videoTitle) {
        val metadataItem = AVMutableMetadataItem().apply {
            setIdentifier(AVMetadataCommonIdentifierTitle)
            setExtendedLanguageTag("und")
            setValue(videoTitle.orEmpty() as NSString)
        }

        playerItem.externalMetadata = playerItem.externalMetadata.toMutableList().also {
            it.add(metadataItem)
        }
    }

    // Check if metadata is enabled or not
    if (enableMediaMetadata) {
        // Set the artist metadata
        LaunchedEffect(playerItem, videoArtist) {
            val metadataItem = AVMutableMetadataItem().apply {
                setIdentifier(AVMetadataCommonIdentifierArtist)
                setExtendedLanguageTag("und")
                setValue(videoArtist.orEmpty() as NSString)
            }

            playerItem.externalMetadata = playerItem.externalMetadata.toMutableList().also {
                it.add(metadataItem)
            }
        }

        // Set the artwork metadata
        IOLaunchedEffect(playerItem, videoArtworkUrl) {
            val artworkData = videoArtworkUrl?.let {
                NSData.dataWithContentsOfURL(NSURL.URLWithString(videoArtworkUrl)!!)
            }

            val metadataItem = AVMutableMetadataItem().apply {
                setIdentifier(AVMetadataCommonIdentifierArtwork)
                setKeySpace(AVMetadataKeySpaceCommon)
                setValue(artworkData)
            }

            playerItem.externalMetadata = playerItem.externalMetadata.toMutableList().also {
                it.add(metadataItem)
            }
        }
    }

    val player = remember(playerItem) {
        AVPlayer(playerItem = playerItem)
    }

    // Update voice state
    LaunchedEffect(enableVoice) {
        player.volume = if (enableVoice) 1f else 0f
    }

    // Player states
    var isPlaying by remember { mutableStateOf(player.rate != 0f) }
    val icon = if (isPlaying) pauseIconRes else playIconRes
    var isPlayButtonVisible by remember { mutableStateOf(true) }
    var isVideoEnded by remember { mutableStateOf(false) }

    val playerLayer = remember(player) {
        AVPlayerLayer().apply {
            this.player = player
            videoGravity = if (scaleToCrop) {
                AVLayerVideoGravityResizeAspectFill
            } else {
                AVLayerVideoGravityResizeAspect
            }
        }
    }

    val playerController = remember(player) {
        AVPlayerViewController().apply {
            this.player = player
            showsPlaybackControls = controlsType == ControlsType.NativeControls
            videoGravity = if (scaleToCrop) {
                AVLayerVideoGravityResizeAspectFill
            } else {
                AVLayerVideoGravityResizeAspect
            }
        }
    }

    var pipController: AVPictureInPictureController? by remember {
        mutableStateOf(null)
    }

    // Activate the audio session if required
    LaunchedEffect(enablePip, handleLifecyclePause) {
        if (enablePip || handleLifecyclePause.not()) {
            AVAudioSession.sharedInstance().apply {
                setCategory(AVAudioSessionCategoryPlayback, null)
                setMode(AVAudioSessionModeMoviePlayback, null)
                setActive(true, null)
            }
        }
    }

    // Create the player controller
    val videoPlayerController = remember(player) {
        object : VideoPlayerController {
            override fun play() {
                player.play()
            }

            override fun pause() {
                player.pause()
            }
        }
    }

    // Launched effect to invoke player created
    LaunchedEffect(videoPlayerController, onPlayerCreated) {
        onPlayerCreated?.invoke(videoPlayerController)
    }

    // Effect to catch duration
    if (onDurationCaught != null) {
        DefaultLaunchedEffect(player, onDurationCaught) {
            val item = player.currentItem
            if (item != null) {
                val durationSeconds = CMTimeGetSeconds(item.asset.duration)
                if (durationSeconds.isFinite()) {
                    onDurationCaught.invoke(durationSeconds.toDuration(DurationUnit.SECONDS))
                }
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        UIKitView(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    isPlayButtonVisible = isPlayButtonVisible.not()
                },
            properties = UIKitInteropProperties(
                interactionMode = if (controlsType == ControlsType.NativeControls) {
                    UIKitInteropInteractionMode.NonCooperative
                } else {
                    UIKitInteropInteractionMode.Cooperative(
                        delayMillis = Int.MAX_VALUE
                    )
                },
            ),
            factory = {
                UIView().apply {
                    addSubview(playerController.view)
                }
            },
            update = { view ->
                // Remove current subviews
                view.subviews().forEach { subView ->
                    (subView as? UIView)?.removeFromSuperview()
                }

                player.volume = if (enableVoice) 1f else 0f

                // Then add the new player view
                view.addSubview(playerController.view)

                // Resize it
                playerLayer.setFrame(view.bounds)
                playerController.view.setFrame(view.bounds)
                playerController.view.layer.frame = view.bounds

                // Initialize PiP controller if required
                if (enablePip && pipController == null && AVPictureInPictureController.isPictureInPictureSupported()) {
                    pipController = AVPictureInPictureController(playerLayer).apply {
                        canStartPictureInPictureAutomaticallyFromInline = true
                    }
                }
                // Auto play if required
                if (autoPlay) {
                    player.play()
                    playerController.player?.play()
                }

                // Start PiP if it's not already running
                pipController.startIfPossible()
            },
            onRelease = {
                player.pause()
                playerController.player?.pause()
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
                                player.pause()
                                isPlaying = false
                                onVideoPaused?.invoke()
                            } else {
                                if (isVideoEnded) {
                                    // Restart the video
                                    player.seekToTime(CMTimeMake(0, 1))
                                    isVideoEnded = false
                                    isPlaying = true
                                }
                                player.play()
                                isPlaying = true
                                onVideoResumed?.invoke()
                            }
                        }
                )
            }
        }
    }

    // Pause player when disposed
    DisposableEffect(player) {
        var hasStartedPlaying = false
        var wasPlaying = false

        val timeObserver = player.addPeriodicTimeObserverForInterval(
            CMTimeMake(1, 1),
            null
        ) {
            val currentlyPlaying = player.rate != 0f
            isPlaying = currentlyPlaying

            if (currentlyPlaying && !hasStartedPlaying) {
                hasStartedPlaying = true
                onVideoStarted?.invoke()
            }

            if (currentlyPlaying && !wasPlaying) {
                onVideoResumed?.invoke()
            } else if (!currentlyPlaying && wasPlaying) {
                onVideoPaused?.invoke()
            }

            wasPlaying = currentlyPlaying
        }

        NSNotificationCenter.defaultCenter.addObserverForName(
            AVPlayerItemDidPlayToEndTimeNotification,
            playerItem,
            null
        ) { _ ->
            if (autoRepeat) {
                // Restart playback from the beginning
                player.seekToTime(CMTimeMake(0, 1))
                player.play()
                isPlaying = true
                isVideoEnded = false
                onVideoLoop?.invoke()
            } else {
                isPlaying = false
                isVideoEnded = true
                onVideoEnd?.invoke()
            }
        }

        // Clean up on disposal
        onDispose {
            player.pause()
            player.removeTimeObserver(timeObserver)
        }
    }
}

private fun AVPictureInPictureController?.startIfPossible() = this?.let {
    if (!isPictureInPictureActive()) {
        startPictureInPicture()
    }
}

private fun AVPictureInPictureController?.stopIfPossible() = this?.let {
    if (isPictureInPictureActive()) {
        stopPictureInPicture()
    }
}