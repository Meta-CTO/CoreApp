package com.metacto.core.ui.imagepicker.crop

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.Foundation.getBytes
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

/**
 * Convert UIImage to Compose ImageBitmap for rendering
 */
@OptIn(ExperimentalForeignApi::class)
fun UIImage.toImageBitmap(): ImageBitmap? {
    // Convert UIImage to PNG data
    val pngData = UIImagePNGRepresentation(this) ?: return null
    val length = pngData.length.toInt()

    if (length == 0) return null

    // Get the bytes
    val bytes = ByteArray(length)
    bytes.usePinned { pinned ->
        pngData.getBytes(pinned.addressOf(0), length.toULong())
    }

    // Create Skia Image from bytes
    val skiaImage = Image.makeFromEncoded(bytes)

    // Convert to Compose ImageBitmap
    return skiaImage.toComposeImageBitmap()
}
