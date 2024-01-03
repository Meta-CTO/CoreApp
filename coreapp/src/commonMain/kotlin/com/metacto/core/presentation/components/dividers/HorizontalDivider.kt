package com.metacto.core.presentation.components.dividers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        color = CoreTheme.colors.outline,
        modifier = modifier.fillMaxHeight()
    )
}