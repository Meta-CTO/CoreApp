package com.metacto.core.presentation.components.animatedList

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.metacto.core.utils.extensions.half
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Suppress("FunctionName")
fun <T> LazyListScope.AnimatedList(
    animateItems: Boolean,
    animDuration: Duration = 500.milliseconds,
    animOffsetY: Dp = 300.dp,
    data: List<T>,
    item: @Composable (Int, T) -> Unit
) {
    itemsIndexed(data) { index, dataItem ->
        // Animate vertical offset
        val offsetY by animateDpAsState(
            targetValue = if (animateItems) 0.dp else animOffsetY,
            animationSpec = animationSpec(animDuration, index),
            label = "Offset animation"
        )

        // Animate alpha
        val alpha by animateFloatAsState(
            targetValue = if (animateItems) 1f else 0f,
            animationSpec = animationSpec(animDuration, index),
            label = "Alpha animation"
        )

        // Render item
        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = offsetY.toPx().toInt()) }
                .alpha(alpha)
        ) {
            item(index, dataItem)
        }
    }
}

@Composable
private fun <T> animationSpec(duration: Duration, index: Int): AnimationSpec<T> {
    return tween(
        durationMillis = duration.inWholeMilliseconds.toInt(),
        easing = FastOutSlowInEasing,
        delayMillis = duration.inWholeMilliseconds.toInt().half() * index
    )
}