package com.metacto.core.presentation.components.audioPlayer

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.media3.common.util.UnstableApi
import com.metacto.core.domain.DiQualifiers
import com.metacto.core.utils.extensions.OnLifecycleEvent
import org.jetbrains.compose.resources.DrawableResource
import org.koin.compose.koinInject

@OptIn(UnstableApi::class)
@Composable
actual fun AudioPlayer(
    modifier: Modifier,
    uniqueId: String,
    audioUrl: String,
    title: String,
    thumbnailUrl: String,
    autoPlay: Boolean,
    handleLifecyclePause: Boolean,
    playIconRes: DrawableResource,
    pauseIconRes: DrawableResource,
    backgroundColor: Color,
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
    // Inject main stuff
    val playerManagers =
        koinInject<MutableMap<String, AudioPlayerManager>>(DiQualifiers.audioPlayerManagers)
    val playerManager = playerManagers.getOrPut(uniqueId) {
        AudioPlayerManager(uniqueId)
    }

    // Player states
    val currentPosition by playerManager.pollCurrentDuration().collectAsState(0)

    // Setup auto play
    LaunchedEffect(playerManager, autoPlay) {
        playerManager.setAutoPlay(autoPlay)
    }

    // Configure the player
    LaunchedEffect(playerManager, audioUrl) {
        playerManager.setMedia(audioUrl = audioUrl)
    }

    // Create the player controller
    val playerController = remember(playerManager) {
        object : AudioPlayerController {
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

    AudioPlayerComponent(
        totalDuration = playerManager.totalDuration.value,
        isPlaying = playerManager.isPlaying.value,
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
        backgroundColor = backgroundColor,
        onPlayClick = playerManager::togglePlay,
        modifier = modifier
    )

    // Handle dispose if required
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