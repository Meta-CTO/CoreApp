package com.metacto.core.presentation.components.pagerIndicator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    activeColor: Color = CoreTheme.colors.primary,
    inactiveColor: Color = activeColor.copy(alpha = 0.3f),
    indicatorHeight: Dp = CoreTheme.spacings.tabIndicatorSize,
    indicatorWidth: Dp = CoreTheme.spacings.tabIndicatorSize,
    spacing: Dp = CoreTheme.spacings.horizontalPagerIndicatorSpacing,
    indicatorShape: Shape = CoreTheme.shapes.horizontalPagerIndicator.indicatorShape,
    activeBorder: BorderStroke? = null,
    inActiveBorder: BorderStroke? = null
) {
    // Get coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Prepare spacings
    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    // Container box
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        // Render in-active dots first
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Render dots
            repeat(pagerState.pageCount) { index ->
                Dot(
                    shape = indicatorShape,
                    border = inActiveBorder,
                    color = inactiveColor,
                    width = indicatorWidth,
                    height = indicatorHeight,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        // Then render active dot
        Dot(
            shape = indicatorShape,
            border = activeBorder,
            color = activeColor,
            width = indicatorWidth,
            height = indicatorHeight,
            modifier = Modifier.offset {
                val scrollPosition = (pagerState.currentPage + pagerState.currentPageOffsetFraction)
                    .coerceIn(
                        minimumValue = 0f,
                        maximumValue = (pagerState.pageCount - 1).coerceAtLeast(0).toFloat()
                    )
                IntOffset(
                    x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                    y = 0
                )
            }
        )
    }
}

@Composable
private fun Dot(
    modifier: Modifier = Modifier,
    shape: Shape,
    border: BorderStroke?,
    color: Color,
    width: Dp,
    height: Dp,
    dotElevation: Dp = CoreTheme.spacings.dotElevation,
    onClick: () -> Unit = {}
) {
    Card(
        shape = shape,
        border = border,
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = modifier
            .width(width)
            .height(height)
            .shadow(dotElevation)
            .noRippleClickable(onClick = onClick)
    ) {
    }
}