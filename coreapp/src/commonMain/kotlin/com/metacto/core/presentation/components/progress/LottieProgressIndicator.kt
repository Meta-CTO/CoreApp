package com.metacto.core.presentation.components.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable
import com.metacto.core.utils.extensions.rememberLottieComposition
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.AssetResource
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState

@Composable
fun LottieProgressIndicator(
    modifier: Modifier = Modifier,
    lottieRes: AssetResource = MR.assets.loading_indicator_anim,
    isBlocking: Boolean = false,
    progressSize: Dp = CoreTheme.spacings.lottieProgressSize
) {
    // Prepare composition
    val composition by rememberLottieComposition(lottieRes)
    val progress = animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    // Prepare clickable modifier
    val clickableModifier = if (isBlocking) Modifier.noRippleClickable {} else Modifier

    // Then render the indicator
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .then(clickableModifier)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress.value },
            modifier = Modifier.size(progressSize)
        )
    }
}