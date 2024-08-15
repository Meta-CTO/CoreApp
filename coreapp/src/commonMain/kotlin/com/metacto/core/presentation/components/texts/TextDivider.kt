package com.metacto.core.presentation.components.texts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun TextDivider(
    modifier: Modifier = Modifier,
    color: Color = CoreTheme.colors.textDivider.dividerColor,
    horizontalPadding: Dp = CoreTheme.spacings.textDividerHorizontalPadding,
    strokeWidth: Dp = CoreTheme.spacings.textDividerStrokeWidth,
    textStyle: TextStyle = CoreTheme.typography.textDividerTextStyle,
    text: String
) {
    // Container
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(horizontalPadding)
    ) {
        // Left line
        Box(
            modifier = Modifier
                .weight(1f)
                .height(strokeWidth)
                .background(color)
        )

        // Text
        Text(
            text = text,
            style = textStyle,
            color = color
        )

        // Right line
        Box(
            modifier = Modifier
                .weight(1f)
                .height(strokeWidth)
                .background(color)
        )
    }
}