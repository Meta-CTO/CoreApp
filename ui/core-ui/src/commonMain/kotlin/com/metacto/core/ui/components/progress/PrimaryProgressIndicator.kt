package com.metacto.core.ui.components.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.ui.theme.CoreTheme
import androidx.compose.ui.graphics.Color

@Composable
fun PrimaryProgressIndicator(
    modifier: Modifier = Modifier,
    isBlocking: Boolean = false,
    color: Color = CoreTheme.colors.primaryProgressIndicator.progressColor
) {
    ProgressIndicator(
        modifier = modifier,
        isBlocking = isBlocking,
        color = color
    )
}