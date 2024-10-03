package com.metacto.core.presentation.components.images

import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeImageBitmap
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.extensions.IOLaunchedEffect
import dev.icerock.moko.resources.AssetResource
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Codec
import org.jetbrains.skia.Data
import org.koin.compose.koinInject

@Composable
actual fun GifImage(
    modifier: Modifier,
    resource: AssetResource
) {
    // Get resource provider
    val resourceProvider = koinInject<IResourceProvider>()

    // Init the codec
    var codec: Codec? by remember {
        mutableStateOf(null)
    }

    // Load it
    IOLaunchedEffect(resource) {
        val imageBytes = resourceProvider.getBytes(resource)
        if (imageBytes != null) {
            codec = Codec.makeFromData(Data.makeFromBytes(imageBytes))
        }
    }

    // Render gif image if possible
    codec?.let {
        val transition = rememberInfiniteTransition()
        val frameIndex by transition.animateValue(
            initialValue = 0,
            targetValue = it.frameCount - 1,
            Int.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 0
                    for ((index, frame) in it.framesInfo.withIndex()) {
                        index at durationMillis
                        durationMillis += frame.duration
                    }
                }
            )
        )

        val bitmap = remember { Bitmap().apply { allocPixels(it.imageInfo) } }
        Canvas(modifier) {
            it.readPixels(bitmap, frameIndex)
            drawImage(bitmap.asComposeImageBitmap())
        }
    }
}