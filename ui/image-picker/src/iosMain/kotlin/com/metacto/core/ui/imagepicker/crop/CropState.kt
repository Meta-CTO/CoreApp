package com.metacto.core.ui.imagepicker.crop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize

/**
 * State holder for image cropping
 */
class CropState(
    private val aspectRatioX: Int?,
    private val aspectRatioY: Int?,
    private val imageSize: IntSize
) {
    var cropRect by mutableStateOf(calculateInitialCropRect())
        private set

    var scale by mutableStateOf(1f)
        private set

    var offset by mutableStateOf(Offset.Zero)
        private set

    /**
     * Calculate initial crop rect centered in the image with correct aspect ratio
     */
    private fun calculateInitialCropRect(): Rect {
        val targetAspectRatio = if (aspectRatioX != null && aspectRatioY != null && aspectRatioX > 0 && aspectRatioY > 0) {
            aspectRatioX.toFloat() / aspectRatioY.toFloat()
        } else {
            1f // Default to square
        }

        val imageWidth = imageSize.width.toFloat()
        val imageHeight = imageSize.height.toFloat()
        val imageAspectRatio = imageWidth / imageHeight

        val (cropWidth, cropHeight) = if (imageAspectRatio > targetAspectRatio) {
            // Image is wider than target aspect ratio
            val height = imageHeight * 0.8f // Use 80% of image height
            val width = height * targetAspectRatio
            width to height
        } else {
            // Image is taller than target aspect ratio
            val width = imageWidth * 0.8f // Use 80% of image width
            val height = width / targetAspectRatio
            width to height
        }

        // Center the crop rect
        val left = (imageWidth - cropWidth) / 2f
        val top = (imageHeight - cropHeight) / 2f

        return Rect(
            offset = Offset(left, top),
            size = Size(cropWidth, cropHeight)
        )
    }

    /**
     * Update crop rect position (dragging)
     */
    fun updateCropPosition(delta: Offset) {
        val newLeft = (cropRect.left + delta.x).coerceIn(0f, imageSize.width - cropRect.width)
        val newTop = (cropRect.top + delta.y).coerceIn(0f, imageSize.height - cropRect.height)

        cropRect = Rect(
            offset = Offset(newLeft, newTop),
            size = cropRect.size
        )
    }

    /**
     * Update crop rect size (resizing from corners/edges)
     */
    fun updateCropSize(sizeDelta: Size, fromCorner: Corner) {
        val aspectRatio = if (aspectRatioX != null && aspectRatioY != null && aspectRatioX > 0 && aspectRatioY > 0) {
            aspectRatioX.toFloat() / aspectRatioY.toFloat()
        } else {
            cropRect.width / cropRect.height
        }

        when (fromCorner) {
            Corner.BottomEnd -> {
                var newWidth = (cropRect.width + sizeDelta.width).coerceIn(100f, imageSize.width - cropRect.left)
                var newHeight = newWidth / aspectRatio

                // Ensure height doesn't exceed image bounds
                if (cropRect.top + newHeight > imageSize.height) {
                    newHeight = imageSize.height - cropRect.top
                    newWidth = newHeight * aspectRatio
                }

                cropRect = Rect(
                    offset = cropRect.topLeft,
                    size = Size(newWidth, newHeight)
                )
            }
            Corner.TopStart -> {
                var newWidth = (cropRect.width - sizeDelta.width).coerceIn(100f, cropRect.right)
                var newHeight = newWidth / aspectRatio

                val newLeft = (cropRect.right - newWidth).coerceAtLeast(0f)
                val newTop = (cropRect.bottom - newHeight).coerceAtLeast(0f)

                cropRect = Rect(
                    offset = Offset(newLeft, newTop),
                    size = Size(newWidth, newHeight)
                )
            }
            Corner.TopEnd -> {
                var newWidth = (cropRect.width + sizeDelta.width).coerceIn(100f, imageSize.width - cropRect.left)
                var newHeight = newWidth / aspectRatio

                val newTop = (cropRect.bottom - newHeight).coerceAtLeast(0f)

                cropRect = Rect(
                    offset = Offset(cropRect.left, newTop),
                    size = Size(newWidth, newHeight)
                )
            }
            Corner.BottomStart -> {
                var newWidth = (cropRect.width - sizeDelta.width).coerceIn(100f, cropRect.right)
                var newHeight = newWidth / aspectRatio

                val newLeft = (cropRect.right - newWidth).coerceAtLeast(0f)

                cropRect = Rect(
                    offset = Offset(newLeft, cropRect.top),
                    size = Size(newWidth, newHeight)
                )
            }
        }
    }

    enum class Corner {
        TopStart, TopEnd, BottomStart, BottomEnd
    }
}

@Composable
fun rememberCropState(
    aspectRatioX: Int?,
    aspectRatioY: Int?,
    imageSize: IntSize
): CropState {
    return remember(aspectRatioX, aspectRatioY, imageSize) {
        CropState(aspectRatioX, aspectRatioY, imageSize)
    }
}