package com.metacto.core.presentation.components.wheelPicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.theme.CoreTheme
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WheelPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    count: Int,
    rowCount: Int,
    size: DpSize = DpSize(
        CoreTheme.spacings.defaultWheelPickerWidth,
        CoreTheme.spacings.defaultWheelPickerHeight
    ),
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    content: @Composable LazyItemScope.(index: Int) -> Unit,
) {
    val lazyListState = rememberLazyListState(startIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress

    LaunchedEffect(isScrollInProgress, count) {
        if (!isScrollInProgress) {
            onScrollFinished(calculateSnappedItemIndex(lazyListState))?.let {
                lazyListState.scrollToItem(it)
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / rowCount),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {}
        }
        LazyColumn(
            modifier = Modifier
                .height(size.height)
                .width(size.width),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = size.height / rowCount * ((rowCount - 1) / 2)),
            flingBehavior = flingBehavior
        ) {
            items(count) { index ->
                val rotationX = calculateAnimatedRotationX(
                    lazyListState = lazyListState,
                    index = index,
                    rowCount = rowCount
                )
                Box(
                    modifier = Modifier
                        .height(size.height / rowCount)
                        .width(size.width)
                        .alpha(
                            calculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                index = index,
                                rowCount = rowCount
                            )
                        )
                        .graphicsLayer {
                            this.rotationX = rotationX
                        },
                    contentAlignment = Alignment.Center
                ) {
                    content(index)
                }
            }
        }
    }
}

private fun calculateSnappedItemIndex(lazyListState: LazyListState): Int {
    val visibleItemsInfo = lazyListState.layoutInfo.visibleItemsInfo
    if (visibleItemsInfo.isEmpty()) return 0

    // This will give us the offset at the bottom of the viewport
    val viewportEndOffset =
        lazyListState.layoutInfo.viewportStartOffset + lazyListState.layoutInfo.viewportSize.height

    // Find the item that has its center closest to the center of the viewport
    val centeredItemIndex = visibleItemsInfo.minByOrNull { itemInfo ->
        val itemCenter = itemInfo.offset + itemInfo.size / 2
        val viewportCenter = (lazyListState.layoutInfo.viewportStartOffset + viewportEndOffset) / 2
        // We need to return the distance here for the comparison
        kotlin.math.abs(viewportCenter - itemCenter)
    }?.index // Get the index of the item

    // Return the index of the item that has its center closest to the center of the viewport
    // If the centered item is null, we fall back to the first visible item index
    return centeredItemIndex ?: lazyListState.firstVisibleItemIndex
}

@Composable
private fun calculateAnimatedAlpha(
    lazyListState: LazyListState,
    index: Int,
    rowCount: Int
): Float {

    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }.value
    val viewPortHeight = layoutInfo.viewportSize.height.toFloat()
    val singleViewPortHeight = viewPortHeight / rowCount

    val centerIndex = remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value
    val centerIndexOffset =
        remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }.value

    val distanceToCenterIndex = index - centerIndex

    val distanceToIndexSnap = abs(distanceToCenterIndex) * singleViewPortHeight.toInt() - when {
        distanceToCenterIndex > 0 -> centerIndexOffset
        distanceToCenterIndex <= 0 -> -centerIndexOffset
        else -> 0
    }

    return if (distanceToIndexSnap in 0..singleViewPortHeight.toInt()) {
        1.2f - (distanceToIndexSnap / singleViewPortHeight)
    } else {
        0.2f
    }
}

@Composable
private fun calculateAnimatedRotationX(
    lazyListState: LazyListState,
    index: Int,
    rowCount: Int
): Float {

    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }.value
    val viewPortHeight = layoutInfo.viewportSize.height.toFloat()
    val singleViewPortHeight = viewPortHeight / rowCount

    val centerIndex = remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value
    val centerIndexOffset =
        remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }.value

    val distanceToCenterIndex = index - centerIndex

    val distanceToIndexSnap = abs(distanceToCenterIndex) * singleViewPortHeight.toInt() - when {
        distanceToCenterIndex > 0 -> centerIndexOffset
        distanceToCenterIndex <= 0 -> -centerIndexOffset
        else -> 0
    }

    val animatedRotationX = -20f * (distanceToIndexSnap / singleViewPortHeight)

    return if (animatedRotationX.isNaN()) {
        0f
    } else {
        animatedRotationX
    }
}

object WheelPickerDefaults {
    @Composable
    fun selectorProperties(
        enabled: Boolean = true,
        shape: Shape = CoreTheme.shapes.xLarge,
        color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
        border: BorderStroke? = BorderStroke(
            CoreTheme.spacings.stroke,
            MaterialTheme.colorScheme.primary
        ),
    ): SelectorProperties = DefaultSelectorProperties(
        enabled = enabled,
        shape = shape,
        color = color,
        border = border
    )
}

interface SelectorProperties {
    @Composable
    fun enabled(): State<Boolean>

    @Composable
    fun shape(): State<Shape>

    @Composable
    fun color(): State<Color>

    @Composable
    fun border(): State<BorderStroke?>
}

@Immutable
internal class DefaultSelectorProperties(
    private val enabled: Boolean,
    private val shape: Shape,
    private val color: Color,
    private val border: BorderStroke?
) : SelectorProperties {

    @Composable
    override fun enabled(): State<Boolean> {
        return rememberUpdatedState(enabled)
    }

    @Composable
    override fun shape(): State<Shape> {
        return rememberUpdatedState(shape)
    }

    @Composable
    override fun color(): State<Color> {
        return rememberUpdatedState(color)
    }

    @Composable
    override fun border(): State<BorderStroke?> {
        return rememberUpdatedState(border)
    }
}