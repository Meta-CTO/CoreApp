package com.metacto.core.presentation.components.images

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metacto.coreApp.MR

@Composable
fun GrayRemoteImage(
    modifier: Modifier = Modifier,
    url: String?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape? = null,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    bgColor: Color? = null,
    quality: FilterQuality = FilterQuality.Medium
) {
    RemoteImage(
        modifier = modifier,
        url = url,
        quality = quality,
        contentDescription = contentDescription,
        contentScale = contentScale,
        shape = shape,
        border = border,
        elevation = elevation,
        placeholderRes = MR.images.no_image,
        bgColor = bgColor
    )
}