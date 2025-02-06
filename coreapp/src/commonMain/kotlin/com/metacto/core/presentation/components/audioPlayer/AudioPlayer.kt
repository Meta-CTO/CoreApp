package com.metacto.core.presentation.components.audioPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.presentation.theme.CoreTheme.shapes
import com.metacto.core.presentation.theme.CoreTheme.spacings
import com.metacto.core.presentation.theme.CoreTheme.typography
import com.metacto.coreApp.resources.*
import org.jetbrains.compose.resources.DrawableResource

interface AudioPlayerController {
    fun play() {}
    fun pause() {}
}

@Composable
expect fun AudioPlayer(
    modifier: Modifier = Modifier,
    uniqueId: String = "default_player_id",
    audioUrl: String,
    title: String,
    thumbnailUrl: String,
    autoPlay: Boolean = false,
    handleLifecyclePause: Boolean = false,
    playIconRes: DrawableResource = Res.drawable.ic_audio_play,
    pauseIconRes: DrawableResource = Res.drawable.ic_audio_pause,
    playIconColor: Color = colors.audioPlayer.playIcon,
    playIconSize: Dp = spacings.audioPlayer.playIconSize,
    durationTextColor: Color = colors.audioPlayer.durationText,
    progressColor: List<Color> = colors.audioPlayer.progress,
    thumbnailShape: Shape = shapes.audioPlayer.thumbnailShape,
    trackerColor: Color = colors.audioPlayer.tracker,
    progressHeight: Dp = spacings.audioPlayer.progressHeight,
    durationTextStyle: TextStyle = typography.audioPlayer.duration,
    durationTextWidth: Dp = spacings.audioPlayer.durationTextWidth,
    progressRadius: Dp = spacings.audioPlayer.progressRadius,
    thumbnailSize: Dp = spacings.audioPlayer.thumbnailSize,
    titleColor: Color = colors.audioPlayer.titleColor,
    titleStyle: TextStyle = typography.audioPlayer.titleStyle,
    thumbnailShadowColor: Color = colors.audioPlayer.thumbnailShadowColor,
    thumbnailElevation: Dp = spacings.audioPlayer.thumbnailElevation,
    progressSpacing: Dp = spacings.audioPlayer.progressSpacing,
    topPadding: Dp = spacings.audioPlayer.topPadding,
    horizontalPadding: Dp = spacings.audioPlayer.horizontalPadding,
    horizontalArrangement: Dp = spacings.audioPlayer.horizontalArrangement,
    onPlayerCreated: ((AudioPlayerController) -> Unit)? = null
)