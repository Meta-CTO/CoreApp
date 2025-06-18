package com.metacto.core.presentation.components.images

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import com.metacto.core.presentation.models.ImageUIModel
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.backgroundIfNotNull
import com.metacto.core.utils.extensions.borderIfNotNull
import com.metacto.core.utils.extensions.clipIfNotNull
import com.metacto.core.utils.extensions.shadowIfNotNull
import com.metacto.core.utils.extensions.shimmerIf
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
    elevation: Dp = CoreTheme.spacings.appImage.elevation,
    bgColor: Color? = null,
    quality: FilterQuality = FilterQuality.Medium,
    crossFade: Boolean = true,
    shimmerLoading: Boolean = false,
    extraHeaders: Map<String, String> = emptyMap(),
    shimmerLoadingColor: Color = CoreTheme.colors.appImagesColors.shimmerLoading,
    crossFadeDuration: Int = DEFAULT_IMAGE_CROSS_FADE_DURATION,
    onError: ((ErrorResult) -> Unit)? = null
) {
    // Prepare network headers
    val networkHeaders = NetworkHeaders.Builder()
        .apply { extraHeaders.forEach { (key, value) -> add(key, value) } }
        .build()

    // Prepare painters
    val placeholder = placeholderPainter ?: placeholderVector?.let { rememberVectorPainter(it) }
    val error = errorPainter ?: errorVector?.let { rememberVectorPainter(it) }
    val fallback = fallbackPainter ?: fallbackVector?.let { rememberVectorPainter(it) }
    var showShimmer by remember { mutableStateOf(shimmerLoading) }

    // Build the model
    val context = LocalPlatformContext.current
    val model = remember(url, image?.getData()) {
        ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data(url ?: image?.getData())
            .httpHeaders(networkHeaders)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .applyIf(crossFade) { crossfade(crossFadeDuration) }
            .listener(onError = { _, error ->
                onError?.invoke(error)
            })
            .build()
    }

    // Handle the modifier to show the shimmer and hide it while loaded
    val imageModifier = modifier
        .shimmerIf(showShimmer)
        .shadowIfNotNull(elevation, shape)
        .clipIfNotNull(shape)
        .run {
            if (showShimmer) background(shimmerLoadingColor)
            else backgroundIfNotNull(bgColor)
        }
        .backgroundIfNotNull(bgColor)
        .borderIfNotNull(border, shape)

    // Render image
    AsyncImage(
        model = model,
        placeholder = if (shimmerLoading) null else placeholderPainter,
        error = error ?: placeholder,
        fallback = fallback ?: placeholder,
        filterQuality = quality,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter,
        modifier = imageModifier,
        onSuccess = {
            showShimmer = false
        },
        onError = {
            showShimmer = false
        }
    )
}