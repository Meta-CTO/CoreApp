package com.metacto.core.presentation.components.images

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import com.metacto.core.utils.extensions.rememberBitmapFromBytes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    imageResource: ImageResource? = null,
    painter: Painter? = null,
    url: String? = null,
    bytes: ByteArray? = null,
    placeholder: ImageResource? = null,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    quality: FilterQuality = FilterQuality.Medium
) {
    // Get image bitmap
    val imageBitmap = rememberBitmapFromBytes(bytes)

    if (imageVector != null) {
        Image(
            imageVector = imageVector,
            modifier = modifier,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    } else if (imageResource != null) {
        Image(
            painter = painterResource(imageResource),
            modifier = modifier,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    } else if (painter != null) {
        Image(
            painter = painter,
            modifier = modifier,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    } else if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            modifier = modifier,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    } else if (url != null) {
        // Render image
        RemoteImage(
            modifier = modifier,
            url = url,
            placeholderRes = placeholder,
            contentDescription = contentDescription,
            contentScale = contentScale,
            quality = quality
        )
    } else if (placeholder != null) {
        Image(
            painter = painterResource(placeholder),
            modifier = modifier,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}