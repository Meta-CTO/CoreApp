package com.metacto.core.presentation.components.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.AssetResource

@Composable
expect fun GifImage(
    modifier: Modifier = Modifier,
    resource: AssetResource
)