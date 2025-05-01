package com.metacto.core.ui.components.numberSelector

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme

@Composable
internal fun NumberItem(
    modifier: Modifier = Modifier,
    number: Int,
    isSelected: Boolean,
    selectedTextColor: Color,
    selectedBackgroundColor: Color,
    unSelectedTextColor: Color,
    unSelectedBackgroundColor: Color,
    size: Dp = CoreTheme.spacings.numberSelectorSize,
    shape:RoundedCornerShape = CoreTheme.shapes.numberItem.shape,
    textStyle :TextStyle = CoreTheme.typography.numberItem.textStyle,
    onClick: () -> Unit
) {
    // Prepare colors
    val textColor by animateColorAsState(
        if (isSelected) selectedTextColor else unSelectedTextColor
    )
    val backgroundColor by animateColorAsState(
        if (isSelected) selectedBackgroundColor else unSelectedBackgroundColor
    )

    // Container box
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(shape)
            .clickable(onClick = onClick)
            .background(backgroundColor)
    ) {
        Text(
            text = number.toString(),
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}