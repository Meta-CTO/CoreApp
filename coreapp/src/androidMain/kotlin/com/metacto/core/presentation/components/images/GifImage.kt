package com.metacto.core.presentation.components.images

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.metacto.core.utils.extensions.getAbsolutePath
import dev.icerock.moko.resources.AssetResource

@Composable
actual fun GifImage(
    modifier: Modifier,
    resource: AssetResource
) {
    // Init image loader
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    // Then render the image
    Image(
        modifier = modifier,
        contentDescription = null,
        painter = rememberAsyncImagePainter(
            imageLoader = imageLoader,
            model = resource.getAbsolutePath()
        )
    )
}