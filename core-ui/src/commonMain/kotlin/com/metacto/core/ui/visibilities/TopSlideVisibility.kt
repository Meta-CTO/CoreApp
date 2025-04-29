package com.metacto.core.ui.visibilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopSlideVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    duration: Int = 700,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        content = content,
        enter = slideInVertically(
            animationSpec = tween(duration),
            initialOffsetY = { -it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(duration),
            targetOffsetY = { -it }
        )
    )
}