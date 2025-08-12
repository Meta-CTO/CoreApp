package com.metacto.core.ui.components.voyager

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.metacto.core.ui.base.CoreScreen


private typealias ScreenTransitionContent = @Composable AnimatedVisibilityScope.(Screen) -> Unit

@ExperimentalAnimationApi
@Composable
private fun ScreenTransition(
    navigator: Navigator,
    transition: AnimatedContentTransitionScope<Screen>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    content: ScreenTransitionContent = {
        (it as? CoreScreen<*>)?.trackLifecycle()
        it.Content()
    }
) {
    AnimatedContent(
        targetState = navigator.lastItem,
        transitionSpec = transition,
        modifier = modifier
    ) { screen ->
        navigator.saveableState("transition", screen) {
            content(screen)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun FadeTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = spring(
        stiffness = Spring.StiffnessMediumLow
    ),
    content: ScreenTransitionContent = {
        (it as? CoreScreen<*>)?.trackLifecycle()
        it.Content()
    }
) {
    ScreenTransition(
        navigator = navigator,
        modifier = modifier,
        content = content,
        transition = { fadeIn(animationSpec = animationSpec) togetherWith fadeOut(animationSpec = animationSpec) }
    )
}
