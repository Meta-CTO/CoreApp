package com.metacto.core.presentation.components.lottie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.compose.readTextAsState
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController
import kotlin.math.roundToInt

@Composable
actual fun LottieAnimation(
    modifier: Modifier,
    animRes: AssetResource,
    isRepeated: Boolean,
    contentScale: ContentScale,
    speed: Float,
) {
    // Init the animation
    var animation by remember(animRes) {
        mutableStateOf<Animation?>(null)
    }

    // Load anim content and create the animation
    val animContent by animRes.readTextAsState()
    animContent?.let {
        animation = Animation.makeFromString(it)
    }

    // And render suitable animation if possible
    when {
        animation != null && isRepeated -> InfiniteAnimation(
            modifier = modifier,
            animation = animation!!,
            contentScale = contentScale,
            speed = speed
        )

        animation != null && isRepeated.not() -> FiniteAnimation(
            modifier = modifier,
            animation = animation!!,
            contentScale = contentScale,
            speed = speed
        )
    }
}


@Composable
private fun InfiniteAnimation(
    modifier: Modifier = Modifier,
    animation: Animation,
    contentScale: ContentScale,
    speed: Float
) {
    // Prepare main objects
    val infiniteTransition = rememberInfiniteTransition()
    val invalidationController = remember { InvalidationController() }

    // Create the progress animator
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = animation.duration,
        animationSpec = infiniteRepeatable(
            animation = tween((animation.duration * 1000 / speed).roundToInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Then render a canvas with the animation
    Canvas(
        modifier = modifier
    ) {
        drawIntoCanvas { canvas ->
            // Prepare scale factor
            val compositionSize = animation.size.let { Size(it.x, it.y) }
            val scale = contentScale.computeScaleFactor(compositionSize, size)

            // Then render
            animation.seekFrameTime(progress, invalidationController)
            animation.render(
                canvas = canvas.nativeCanvas,
                dst = Rect.makeWH(
                    w = animation.width * scale.scaleX,
                    h = animation.height * scale.scaleY
                )
            )
        }
    }
}

@Composable
private fun FiniteAnimation(
    modifier: Modifier = Modifier,
    animation: Animation,
    contentScale: ContentScale,
    speed: Float
) {
    // Prepare the progress animator
    val progress = remember { Animatable(0f) }
    LaunchedEffect(animation) {
        progress.animateTo(
            targetValue = animation.duration,
            animationSpec = tween((animation.duration * 1000 / speed).roundToInt(), easing = LinearEasing)
        )
    }

    // Then render a canvas with the animation
    Canvas(
        modifier = modifier
    ) {
        drawIntoCanvas { canvas ->
            // Prepare scale factor
            val compositionSize = animation.size.let { Size(it.x, it.y) }
            val scale = contentScale.computeScaleFactor(compositionSize, size)

            // Then render
            animation.seekFrameTime(progress.value)
            animation.render(
                canvas = canvas.nativeCanvas,
                dst = Rect.makeWH(
                    w = animation.width * scale.scaleX,
                    h = animation.height * scale.scaleY
                )
            )
        }
    }
}