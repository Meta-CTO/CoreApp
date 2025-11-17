package com.metacto.core.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalCoilApi::class)
suspend fun String.downloadBitmap(context: Context): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .allowHardware(false)
        .build()

    return when (val result = loader.execute(request)) {
        is SuccessResult -> result.image.toBitmap()
        else -> null
    }
}

/**
 * Normalizes the image orientation based on EXIF data and returns the corrected byte array
 */
fun Uri.normalizeImageOrientation(context: Context): ByteArray? {
    return try {
        // Read the image bytes
        val inputStream = context.contentResolver.openInputStream(this) ?: return null
        val originalBytes = inputStream.use { it.readBytes() }

        // Decode the bitmap
        val bitmap = BitmapFactory.decodeByteArray(originalBytes, 0, originalBytes.size)
            ?: return originalBytes

        // Read EXIF orientation
        val exif = context.contentResolver.openInputStream(this)?.use { stream ->
            ExifInterface(stream)
        }

        val orientation = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        ) ?: ExifInterface.ORIENTATION_NORMAL

        // Calculate rotation angle
        val rotationAngle = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }

        // If no rotation needed, return original bytes
        if (rotationAngle == 0f) {
            return originalBytes
        }

        // Rotate the bitmap
        val matrix = Matrix().apply {
            postRotate(rotationAngle)
        }

        val rotatedBitmap = Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )

        // Convert back to byte array
        val outputStream = ByteArrayOutputStream()
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val result = outputStream.toByteArray()

        // Clean up
        if (rotatedBitmap != bitmap) {
            rotatedBitmap.recycle()
        }
        bitmap.recycle()

        result
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}