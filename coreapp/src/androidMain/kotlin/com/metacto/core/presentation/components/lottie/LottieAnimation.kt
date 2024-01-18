package com.metacto.core.presentation.components.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.icerock.moko.resources.AssetResource
import com.airbnb.lottie.compose.LottieAnimation as NativeLottieAnimation

@Composable
actual fun LottieAnimation(
    modifier: Modifier,
    animRes: AssetResource,
    isRepeated: Boolean,
    contentScale: ContentScale
) {
    // Load the lottie composition
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(animRes.path))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (isRepeated) LottieConstants.IterateForever else 1
    )

    // Render animation
    NativeLottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
        contentScale = contentScale
    )
}
