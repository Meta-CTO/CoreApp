package com.metacto.core.presentation.components.images

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metacto.core.utils.extensions.backgroundIfNotNull
import com.metacto.core.utils.extensions.borderIfNotNull
import com.metacto.core.utils.extensions.clipIfNotNull
import com.metacto.core.utils.extensions.shadowIfNotNull
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.core.ExperimentalKamelApi
import io.kamel.image.KamelImage
import io.kamel.image.PainterFailure
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalKamelApi::class)
@Composable
fun RemoteImage(
    modifier: Modifier = Modifier,
    url: String?,
    placeholderRes: ImageResource? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape? = null,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    bgColor: Color? = null,
    quality: FilterQuality = FilterQuality.Medium
) {
    // Prepare placeholder painter
    val placeholderPainter = placeholderRes?.let {
        Result.success(painterResource(it))
    }

    // Render image
    KamelImage(
        resource = asyncPainterResource(
            data = url.orEmpty(),
            filterQuality = quality,
            onLoadingPainter = {
                placeholderPainter ?: Result.failure(PainterFailure())
            },
            onFailurePainter = {
                placeholderPainter ?: Result.failure(PainterFailure())
            }
        ),
        contentDescription = contentDescription,
        contentScale = contentScale,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        ),
        modifier = modifier
            .shadowIfNotNull(elevation, shape)
            .clipIfNotNull(shape)
            .borderIfNotNull(border, shape)
            .backgroundIfNotNull(bgColor)
    )
}