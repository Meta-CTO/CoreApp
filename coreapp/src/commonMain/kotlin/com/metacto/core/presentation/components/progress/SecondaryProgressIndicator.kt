package com.metacto.core.presentation.components.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun SecondaryProgressIndicator(
    modifier: Modifier = Modifier,
    isBlocking: Boolean = false,
    color: Color = CoreTheme.colors.secondaryProgressIndicator.progressColor
) {
    ProgressIndicator(
        modifier = modifier,
        isBlocking = isBlocking,
        color = color
    )
}