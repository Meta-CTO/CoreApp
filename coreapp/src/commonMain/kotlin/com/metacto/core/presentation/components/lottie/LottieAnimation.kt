package com.metacto.core.presentation.components.lottie

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import dev.icerock.moko.resources.AssetResource

@Composable
expect fun LottieAnimation(
    modifier: Modifier = Modifier,
    animRes: AssetResource,
    isRepeated: Boolean = false,
    contentScale: ContentScale = ContentScale.Fit
)
