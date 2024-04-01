package com.metacto.core.presentation.components.images

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.metacto.core.presentation.models.ImageUIModel
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.backgroundIfNotNull
import com.metacto.core.utils.extensions.borderIfNotNull
import com.metacto.core.utils.extensions.clipIfNotNull
import com.metacto.core.utils.extensions.shadowIfNotNull
import com.metacto.strapikmm.util.applyIf

internal const val DEFAULT_IMAGE_CROSS_FADE_DURATION = 200

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    url: String? = null,
    image: ImageUIModel? = null,
    placeholderPainter: Painter? = null,
    placeholderVector: ImageVector? = null,
    errorPainter: Painter? = null,
    errorVector: ImageVector? = null,
    fallbackPainter: Painter? = null,
    fallbackVector: ImageVector? = null,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    shape: Shape? = null,
    border: BorderStroke? = null,
    elevation: Dp = CoreTheme.spacings.noSpacing,
    bgColor: Color? = null,
    quality: FilterQuality = FilterQuality.Medium,
    crossFade: Boolean = true,
    crossFadeDuration: Int = DEFAULT_IMAGE_CROSS_FADE_DURATION
) {
    // Prepare painters
    val placeholder = placeholderPainter ?: placeholderVector?.let { rememberVectorPainter(it) }
    val error = errorPainter ?: errorVector?.let { rememberVectorPainter(it) }
    val fallback = fallbackPainter ?: fallbackVector?.let { rememberVectorPainter(it) }

    // Build the model
    val context = LocalPlatformContext.current
    val model = remember(url, image?.getData()) {
        ImageRequest.Builder(context)
            .data(url ?: image?.getData())
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .applyIf(crossFade) { crossfade(crossFadeDuration) }
            .build()
    }

    // Render image
    AsyncImage(
        model = model,
        placeholder = placeholder,
        error = error ?: placeholder,
        fallback = fallback ?: placeholder,
        filterQuality = quality,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter,
        modifier = modifier
            .shadowIfNotNull(elevation, shape)
            .clipIfNotNull(shape)
            .backgroundIfNotNull(bgColor)
            .borderIfNotNull(border, shape)
    )
}