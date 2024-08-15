package com.metacto.core.presentation.components.dividers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = CoreTheme.colors.horizontalDivider.dividerColor
) {
    Divider(
        color = color,
        modifier = modifier.fillMaxHeight()
    )
}