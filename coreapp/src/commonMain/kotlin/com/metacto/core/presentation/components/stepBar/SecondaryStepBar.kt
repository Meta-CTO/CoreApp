package com.metacto.core.presentation.components.stepBar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun SecondaryStepBar(
    modifier: Modifier = Modifier,
    stepsCount: Int,
    currentStep: Int,
    height: Dp = CoreTheme.spacings.stepBarHeight,
    strokeWidth: Dp = CoreTheme.spacings.stepBarStroke,
    color: Color = CoreTheme.colors.secondaryStepBar.color,
    borderColor: Color = CoreTheme.colors.secondaryStepBar.borderColor,
    progressShape: RoundedCornerShape = CoreTheme.shapes.secondaryStepBarProgressShape
) {
    // Prepare progress
    val progress by animateFloatAsState(
        currentStep.toFloat() / stepsCount.toFloat()
    )

    // Render indicator
    LinearProgressIndicator(
        progress = if (progress.isNaN()) 0f else progress,
        color = color,
        trackColor = Color.Transparent,
        strokeCap = StrokeCap.Round,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(
                width = strokeWidth,
                color = borderColor,
                shape = progressShape
            )
    )
}