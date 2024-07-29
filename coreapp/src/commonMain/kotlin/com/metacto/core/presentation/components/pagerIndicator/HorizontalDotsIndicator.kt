package com.metacto.core.presentation.components.pagerIndicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalDotsIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    activeColor: Color = CoreTheme.colors.primary,
    inactiveColor: Color = CoreTheme.colors.onPrimary.copy(alpha = 0.5f),
    activeSize: Dp = CoreTheme.spacings.dotIndicatorActiveSize,
    inActiveSize: Dp = CoreTheme.spacings.dotIndicatorInActiveSize,
    spacing: Dp = CoreTheme.spacings.horizontalDotsIndicatorSpacing
) {
    // Get coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Container row
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = spacing,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        // Render dots
        repeat(pagerState.pageCount) { index ->
            // Prepare color
            val isSelected = pagerState.currentPage == index
            val color by animateColorAsState(
                targetValue = if (isSelected) activeColor else inactiveColor,
                label = "Horizontal Indicator Dot Color"
            )
            val size by animateDpAsState(
                targetValue = if (isSelected) activeSize else inActiveSize,
                label = "Horizontal Indicator Dot Size"
            )

            // Then render the dot
            Dot(
                color = color,
                size = size,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@Composable
private fun Dot(
    modifier: Modifier = Modifier,
    color: Color,
    size: Dp,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
            .noRippleClickable(onClick = onClick)
    )
}