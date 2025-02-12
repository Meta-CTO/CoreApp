package com.metacto.core.presentation.components.audioPlayer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.components.images.AppImage
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.utils.extensions.noRippleClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun AudioPlayerComponent(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
    thumbnailShape: Shape,
    thumbnailSize: Dp,
    title: String,
    titleColor: Color,
    titleStyle: TextStyle,
    totalDuration: Long,
    isPlaying: Boolean,
    playIconRes: DrawableResource,
    pauseIconRes: DrawableResource,
    playIconColor: Color,
    playIconSize: Dp,
    progressColor: List<Color>,
    trackerColor: Color,
    progressHeight: Dp,
    progressRadius: Dp,
    thumbnailShadowColor: Color,
    thumbnailElevation: Dp,
    progressSpacing: Dp,
    topPadding: Dp,
    horizontalPadding: Dp,
    horizontalArrangement: Dp,
    thumbnailExtraHeaders: Map<String, String> = emptyMap(),
    onPlayClick: () -> Unit
) {
    // prepare vars
    val icon = if (isPlaying) pauseIconRes else playIconRes

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

    Column(
        verticalArrangement = Arrangement.spacedBy(progressSpacing),
        modifier = modifier
            .fillMaxWidth()
            .background(color = colors.white)
            .padding(top = topPadding)
    ) {
        // Main content row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(horizontalArrangement),
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .fillMaxWidth()
        ) {
            // Thumbnail
            AppImage(
                url = thumbnailUrl,
                contentScale = ContentScale.Crop,
                extraHeaders = thumbnailExtraHeaders,
                modifier = Modifier
                    .shadow(
                        elevation = thumbnailElevation,
                        shape = thumbnailShape,
                        spotColor = thumbnailShadowColor,
                        ambientColor = thumbnailShadowColor
                    )
                    .size(thumbnailSize)
                    .clip(thumbnailShape)
            )

            // Title
            SingleLineText(
                text = title,
                style = titleStyle,
                color = titleColor,
                modifier = Modifier.weight(1f)
            )

            // Play/Pause Icon
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = playIconColor,
                modifier = Modifier
                    .size(playIconSize)
                    .noRippleClickable(onClick = onPlayClick),
            )

        }
        // Progress bar
        AudioPlayerProgressIndicator(
            progress = progress.value, // 70% progress
            progressColor = progressColor, // Gradient from blue to magenta
            height = progressHeight,
            trackColor = trackerColor,
            cornerRadius = progressRadius,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AudioPlayerProgressIndicator(
    modifier: Modifier,
    progress: Float,
    trackColor: Color,
    height: Dp,
    cornerRadius: Dp,
    progressColor: List<Color>,

    ) {
    val brush = Brush.horizontalGradient(progressColor)
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
            brush = brush,
            size = Size(progressWidth, trackHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
    }
}