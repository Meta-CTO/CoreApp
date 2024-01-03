package com.metacto.core.presentation.components.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.coreApp.MR
import com.metacto.core.presentation.components.lottie.LottieAnimation
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable
import dev.icerock.moko.resources.AssetResource

@Composable
fun LottieProgressIndicator(
    modifier: Modifier = Modifier,
    lottieRes: AssetResource = MR.assets.loading_indicator_anim,
    isBlocking: Boolean = false
) {
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
            animRes = lottieRes,
            isRepeated = true,
            modifier = Modifier.size(
                CoreTheme.spacings.lottieProgressSize
            )
        )
    }
}