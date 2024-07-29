package com.metacto.core.presentation.components.dividers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = CoreTheme.colors.dividerColor
) {
    Divider(
        color = color,
        modifier = modifier.fillMaxWidth()
    )
}