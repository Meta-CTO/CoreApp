package com.metacto.core.presentation.components.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme
import androidx.compose.ui.graphics.Color

@Composable
fun PrimaryProgressIndicator(
    modifier: Modifier = Modifier,
    isBlocking: Boolean = false,
    color: Color = CoreTheme.colors.primaryProgressColor
) {
    ProgressIndicator(
        modifier = modifier,
        isBlocking = isBlocking,
        color = color
    )
}