package com.metacto.core.presentation.components.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PrimaryProgressIndicator(
    modifier: Modifier = Modifier,
    isBlocking: Boolean = false
) {
    ProgressIndicator(
        modifier = modifier,
        isBlocking = isBlocking,
        color = CoreTheme.colors.primary
    )
}