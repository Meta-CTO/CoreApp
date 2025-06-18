package com.metacto.core.ui.components.visibilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.Ref

@Composable
fun <T> NullabilityAnimatedVisibility(
    value: T?,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn(),
    exit: ExitTransition = fadeOut(),
    content: @Composable (T) -> Unit
) {
    val lastNonNullValue = remember { Ref<T>() }

    if (value != null) {
        lastNonNullValue.value = value
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = value != null,
        enter = enter,
        exit = exit
    ) {
        lastNonNullValue.value?.let { content(it) }
    }
}