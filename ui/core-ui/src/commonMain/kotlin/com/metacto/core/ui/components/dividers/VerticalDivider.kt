package com.metacto.core.ui.components.dividers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = CoreTheme.colors.verticalDivider.dividerColor
) {
    Divider(
        color = color,
        modifier = modifier.fillMaxWidth()
    )
}