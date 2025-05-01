package com.metacto.core.ui.components.images

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.models.ImageUIModel
import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.img_gray_placeholder
import com.metacto.core.ui.theme.CoreTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun GrayAppImage(
    modifier: Modifier = Modifier,
    url: String? = null,
    image: ImageUIModel? = null,
    errorPainter: Painter? = null,
    errorVector: ImageVector? = null,
    fallbackPainter: Painter? = null,
    fallbackVector: ImageVector? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape? = null,
    border: BorderStroke? = null,
    elevation: Dp = CoreTheme.spacings.grayAppImage.elevation,
    bgColor: Color? = null,
    quality: FilterQuality = FilterQuality.Medium,
    crossFade: Boolean = true,
    shimmerLoading: Boolean = false,
    extraHeaders: Map<String, String> = emptyMap(),
    shimmerLoadingColor: Color = CoreTheme.colors.appImagesColors.shimmerLoading,
    crossFadeDuration: Int = DEFAULT_IMAGE_CROSS_FADE_DURATION
) {
    AppImage(
        modifier = modifier,
        url = url,
        image = image,
        quality = quality,
        crossFade = crossFade,
        crossFadeDuration = crossFadeDuration,
        contentDescription = contentDescription,
        contentScale = contentScale,
        shape = shape,
        border = border,
        elevation = elevation,
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter,
        bgColor = bgColor,
        errorPainter = errorPainter,
        errorVector = errorVector,
        fallbackPainter = fallbackPainter,
        fallbackVector = fallbackVector,
        shimmerLoading = shimmerLoading,
        extraHeaders = extraHeaders,
        shimmerLoadingColor = shimmerLoadingColor,
        placeholderPainter = painterResource(Res.drawable.img_gray_placeholder)
    )
}