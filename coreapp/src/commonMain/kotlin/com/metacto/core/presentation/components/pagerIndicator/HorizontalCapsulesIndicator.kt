package com.metacto.core.presentation.components.pagerIndicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCapsulesIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    size: Dp = CoreTheme.spacings.horizontalCapsulesIndicator.size,
    activeColor: Color = CoreTheme.colors.horizontalCapsulesIndicator.activeColor,
    inActiveColor: Color = CoreTheme.colors.horizontalCapsulesIndicator.inActiveColor,
    spacing: Dp = CoreTheme.spacings.horizontalCapsulesIndicator.spacing
) {
    // Render capsules row
    Row(
        modifier = modifier.height(size),
        horizontalArrangement = Arrangement.spacedBy(
            space = spacing,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        // Loop to draw capsules
        for (index in 0 until pagerState.pageCount) {
            // Prepare width
            val width by animateDpAsState(
                targetValue = when (pagerState.currentPage) {
                    index -> size * 2
                    else -> size
                },
                label = "Capsule Width"
            )

            // Prepare color
            val isSelected = index == pagerState.currentPage
            val color by animateColorAsState(
                targetValue = if (isSelected) activeColor else inActiveColor,
                label = "Capsule Color"
            )

            // Render capsule
            Capsule(
                color = color,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(width)
            )
        }
    }
}

@Composable
private fun Capsule(
    modifier: Modifier,
    color: Color
) {
    Box(
        modifier = modifier.background(
            color = color,
            shape = CircleShape
        )
    )
}