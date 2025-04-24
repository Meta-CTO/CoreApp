package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

@Composable
fun <T> InfiniteScrollLazyRow(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    startAnimDelay: Duration = Duration.ZERO,
    scrollSpeed: Float = 10f,
    scrollDelay: Duration = Duration.ZERO,
    itemWidth: Float = 100f,
    items: List<T>,
    itemContent: @Composable (Int, T) -> Unit
) {
    // Mutable list to hold items and enable dynamic adding
    val allItems = remember(items) {
        mutableStateListOf<T>().apply { addAll(items) }
    }

    // LazyRow state to track scroll position
    val listState = rememberLazyListState()

    // Trigger load more items when the last item becomes visible
    LaunchedEffect(allItems) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: return@collect
                if (lastVisibleItemIndex == allItems.lastIndex) {
                    allItems.addAll(items)
                }
            }
    }

    // Animate the scroll position smoothly across all items
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(allItems) {
        var currentOffset = 0f
        val totalItemsWidth = allItems.size * itemWidth

        // Delay the start of the animation if required
        if (startAnimDelay > Duration.ZERO) {
            delay(startAnimDelay)
        }

        // Then start the infinite scroll animation
        while (true) {
            delay(scrollDelay)

            coroutineScope.launch {
                listState.animateScrollBy(scrollSpeed)
            }

            currentOffset += scrollSpeed
            if (currentOffset >= totalItemsWidth) {
                currentOffset = 0f
            }
        }
    }

    // Lazy row container
    LazyRow(
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = false,
        state = listState,
        modifier = modifier
    ) {
        itemsIndexed(allItems) { index, item ->
            itemContent(index, item)
        }
    }
}