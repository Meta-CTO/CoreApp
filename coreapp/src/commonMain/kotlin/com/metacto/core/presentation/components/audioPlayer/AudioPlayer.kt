package com.metacto.core.presentation.components.audioPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.presentation.theme.CoreTheme.spacings
import com.metacto.core.presentation.theme.CoreTheme.typography
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.ImageResource

interface AudioPlayerController {
    fun play() {}
    fun pause() {}
}

@Composable
expect fun AudioPlayer(
    modifier: Modifier = Modifier,
    uniqueId: String = "default_player_id",
    audioUrl: String,
    autoPlay: Boolean = false,
    handleLifecyclePause: Boolean = false,
    playIconRes: ImageResource = MR.images.ic_audio_play,
    pauseIconRes: ImageResource = MR.images.ic_audio_pause,
    playIconColor: Color = colors.audioPlayer.playIcon,
    playIconSize: Dp = spacings.audioPlayer.playIconSize,
    durationTextColor: Color = colors.audioPlayer.durationText,
    progressColor: Color = colors.audioPlayer.progress,
    trackerColor: Color = colors.audioPlayer.tracker,
    progressHeight: Dp = spacings.audioPlayer.progressHeight,
    durationTextStyle: TextStyle = typography.audioPlayer.duration,
    durationTextWidth: Dp = spacings.audioPlayer.durationTextWidth,
    progressRadius: Dp = spacings.audioPlayer.progressRadius,
    onPlayerCreated: ((AudioPlayerController) -> Unit)? = null
)