package com.metacto.core.presentation.components.numberSelector

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.theme.CoreTheme

@Composable
internal fun NumberItem(
    modifier: Modifier = Modifier,
    number: Int,
    isSelected: Boolean,
    selectedTextColor: Color,
    selectedBackgroundColor: Color,
    unSelectedTextColor: Color,
    unSelectedBackgroundColor: Color,
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
            .size(CoreTheme.spacings.numberSelectorSize)
            .clip(CoreTheme.shapes.circle)
            .clickable(onClick = onClick)
            .background(backgroundColor)
    ) {
        Text(
            text = number.toString(),
            style = CoreTheme.typography.numberSelector,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}