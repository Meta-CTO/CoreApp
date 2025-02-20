package com.metacto.core.presentation.components.audioPlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.DrawableResource
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.rate
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.seekToTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun AudioPlayer(
    modifier: Modifier,
    uniqueId: String,
    audioUrl: String,
    title: String,
    thumbnailUrl: String,
    audioPlayerStatusListener: AudioPlayerStatusListener,
    autoPlay: Boolean,
    handleLifecyclePause: Boolean,
    playIconRes: DrawableResource,
    pauseIconRes: DrawableResource,
    playIconColor: Color,
    playIconSize: Dp,
    durationTextColor: Color,
    progressColor: List<Color>,
    thumbnailShape: Shape,
    trackerColor: Color,
    progressHeight: Dp,
    durationTextStyle: TextStyle,
    durationTextWidth: Dp,
    progressRadius: Dp,
    thumbnailSize: Dp,
    titleColor: Color,
    titleStyle: TextStyle,
    thumbnailShadowColor: Color,
    thumbnailElevation: Dp,
    progressSpacing: Dp,
    topPadding: Dp,
    horizontalPadding: Dp,
    horizontalArrangement: Dp,
    onPlayerCreated: ((AudioPlayerController) -> Unit)?
) {

    // Create the player item with the url
    val playerItem = remember(audioUrl) {
        val nsUrl = if (NSURL.fileURLWithPath(audioUrl).isFileURL()) {
            NSURL.fileURLWithPath(audioUrl)
        } else {
            NSURL.URLWithString(audioUrl)!!
        }
        AVPlayerItem(uRL = nsUrl)
    }

    val player = remember(playerItem) {
        AVPlayer(playerItem = playerItem)
    }

    // isPlaying state
    var isPlaying by remember { mutableStateOf(player.rate != 0f) }

    // Track audio ended state
    var isAudioEnded by remember { mutableStateOf(false) }

    // Track total duration and current position
    var totalDuration by remember { mutableStateOf(0L) }
    var currentPosition by remember { mutableStateOf(0L) }

    // Observe changes to the player state
    DisposableEffect(player) {
        val timeObserver = player.addPeriodicTimeObserverForInterval(
            CMTimeMake(1, 1),
            null
        ) {
            val currentTime = player.currentTime()
            currentPosition = (CMTimeGetSeconds(currentTime) * 1000).toLong()
            isPlaying = player.rate != 0f
        }

        NSNotificationCenter.defaultCenter.addObserverForName(
            AVPlayerItemDidPlayToEndTimeNotification,
            playerItem,
            null
        ) { _ ->
            isPlaying = false
            isAudioEnded = true
        }

        // Clean up on disposal
        onDispose {
            player.removeTimeObserver(timeObserver)
        }
    }

    // Update total duration when the player item changes
    LaunchedEffect(player.currentItem) {
        val duration = player.currentItem?.duration
        totalDuration = if (duration != null) {
            (CMTimeGetSeconds(duration) * 1000).toLong() // Convert to milliseconds
        } else {
            0L
        }
    }

    // Create the player controller
    val audioPlayerController = remember(player) {
        object : AudioPlayerController {
            override fun play() {
                player.play()
                audioPlayerStatusListener.onAudioPlayed()
            }

            override fun pause() {
                player.pause()
                audioPlayerStatusListener.onAudioPaused()
            }
        }
    }

    // Launched effect to invoke player created
    LaunchedEffect(audioPlayerController, onPlayerCreated) {
        onPlayerCreated?.invoke(audioPlayerController)
    }

    AudioPlayerComponent(
        totalDuration = totalDuration,
        isPlaying = isPlaying,
        playIconRes = playIconRes,
        pauseIconRes = pauseIconRes,
        playIconColor = playIconColor,
        playIconSize = playIconSize,
        thumbnailUrl = thumbnailUrl,
        thumbnailShape = thumbnailShape,
        thumbnailSize = thumbnailSize,
        title = title,
        titleColor = titleColor,
        titleStyle = titleStyle,
        progressColor = progressColor,
        trackerColor = trackerColor,
        progressHeight = progressHeight,
        progressRadius = progressRadius,
        thumbnailShadowColor = thumbnailShadowColor,
        thumbnailElevation = thumbnailElevation,
        horizontalArrangement = horizontalArrangement,
        horizontalPadding = horizontalPadding,
        progressSpacing = progressSpacing,
        topPadding = topPadding,
        onPlayClick = {
            if (isPlaying) {
                player.pause()
                audioPlayerStatusListener.onAudioPaused()
            } else {
                if (isAudioEnded) {
                    // Restart the video
                    player.seekToTime(CMTimeMake(0, 1))
                    isAudioEnded = false
                    isPlaying = true
                }
                player.play()
                audioPlayerStatusListener.onAudioPlayed()
                isPlaying = true
            }
        },
        modifier = modifier
    )

    // Pause player if needed
    DisposableEffect(player) {
        onDispose {
            if (handleLifecyclePause) {
                player.pause()
                audioPlayerStatusListener.onAudioPaused()
            }
        }
    }
}