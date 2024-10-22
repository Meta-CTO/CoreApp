package com.metacto.core.presentation.components.audioPlayer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme.spacings
import com.metacto.core.utils.extensions.formatSecondsToMMSS
import com.metacto.core.utils.extensions.noRippleClickable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
internal fun AudioPlayerComponent(
    modifier: Modifier = Modifier,
    totalDuration: Long,
    currentPosition: Long,
    isPlaying: Boolean,
    playIconRes: ImageResource,
    pauseIconRes: ImageResource,
    playIconColor: Color,
    playIconSize: Dp,
    durationTextColor: Color,
    durationTextStyle: TextStyle,
    progressColor: Color,
    trackerColor: Color,
    progressHeight: Dp,
    durationTextWidth: Dp,
    progressRadius: Dp,
    onPlayClick: () -> Unit
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isPlaying) {
        if (progress.value == 1f) {
            progress.snapTo(0f)
        }

        if (isPlaying) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = totalDuration.toInt(),
                    easing = LinearEasing
                )
            )
        } else {
            progress.stop()
        }
    }

    // Prepare icon
    val icon = if (isPlaying) pauseIconRes else playIconRes

    // Row container
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacings.paddingLarge),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Play icon
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = playIconColor,
            modifier = Modifier
                .size(playIconSize)
                .noRippleClickable(onClick = onPlayClick)
        )

        // Progress indicator
        PlayerProgressIndicator(
            color = progressColor,
            trackColor = trackerColor,
            progress = progress.value,
            height = progressHeight,
            cornerRadius = progressRadius,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        // Duration text
        Text(
            text = (currentPosition / 1000).toInt().formatSecondsToMMSS(),
            color = durationTextColor,
            style = durationTextStyle,
            modifier = Modifier.width(durationTextWidth)
        )
    }
}

@Composable
private fun PlayerProgressIndicator(
    modifier: Modifier,
    progress: Float,
    color: Color,
    trackColor: Color,
    height: Dp,
    cornerRadius: Dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val trackHeight = size.height
        val progressWidth = size.width * progress

        // Draw track with rounded corners
        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )

        // Draw progress with rounded corners
        drawRoundRect(
            color = color,
            size = Size(progressWidth, trackHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}