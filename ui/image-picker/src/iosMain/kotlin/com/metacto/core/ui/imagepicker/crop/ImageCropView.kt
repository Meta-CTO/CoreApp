package com.metacto.core.ui.imagepicker.crop

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIImage

@OptIn(ExperimentalForeignApi::class)
@Composable
fun ImageCropView(
    image: UIImage,
    imageBitmap: ImageBitmap,
    aspectRatioX: Int?,
    aspectRatioY: Int?,
    onCropComplete: (UIImage) -> Unit,
    onCancel: () -> Unit
) {
    val imageSize = remember {
        image.size.useContents {
            IntSize(width.toInt(), height.toInt())
        }
    }

    val cropState = rememberCropState(
        aspectRatioX = aspectRatioX,
        aspectRatioY = aspectRatioY,
        imageSize = imageSize
    )

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var isDraggingCrop by remember { mutableStateOf(false) }
    var isResizing by remember { mutableStateOf(false) }
    var resizeCorner by remember { mutableStateOf(CropState.Corner.BottomEnd) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Crop area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        containerSize = coordinates.size
                    }
            ) {
                if (containerSize != IntSize.Zero) {
                    // Calculate scale to fit image in container
                    val scale = calculateScaleToFit(
                        imageSize = imageSize.toSize(),
                        containerSize = containerSize.toSize()
                    )

                    val displaySize = Size(
                        imageSize.width * scale,
                        imageSize.height * scale
                    )

                    // Center the image in the container
                    val imageOffset = Offset(
                        (containerSize.width - displaySize.width) / 2f,
                        (containerSize.height - displaySize.height) / 2f
                    )

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        val cropRectDisplay = scaleCropRect(cropState.cropRect, scale, imageOffset)

                                        // Check if touching a corner (for resizing)
                                        val cornerRadius = 40f
                                        when {
                                            isNearPoint(offset, cropRectDisplay.topLeft, cornerRadius) -> {
                                                isResizing = true
                                                resizeCorner = CropState.Corner.TopStart
                                            }
                                            isNearPoint(offset, cropRectDisplay.topRight, cornerRadius) -> {
                                                isResizing = true
                                                resizeCorner = CropState.Corner.TopEnd
                                            }
                                            isNearPoint(offset, cropRectDisplay.bottomLeft, cornerRadius) -> {
                                                isResizing = true
                                                resizeCorner = CropState.Corner.BottomStart
                                            }
                                            isNearPoint(offset, cropRectDisplay.bottomRight, cornerRadius) -> {
                                                isResizing = true
                                                resizeCorner = CropState.Corner.BottomEnd
                                            }
                                            cropRectDisplay.contains(offset) -> {
                                                isDraggingCrop = true
                                            }
                                        }
                                    },
                                    onDrag = { _, dragAmount ->
                                        if (isDraggingCrop) {
                                            cropState.updateCropPosition(dragAmount / scale)
                                        } else if (isResizing) {
                                            val sizeDelta = when (resizeCorner) {
                                                CropState.Corner.BottomEnd, CropState.Corner.TopEnd ->
                                                    Size(dragAmount.x / scale, dragAmount.y / scale)
                                                else ->
                                                    Size(-dragAmount.x / scale, -dragAmount.y / scale)
                                            }
                                            cropState.updateCropSize(sizeDelta, resizeCorner)
                                        }
                                    },
                                    onDragEnd = {
                                        isDraggingCrop = false
                                        isResizing = false
                                    }
                                )
                            }
                    ) {
                        // Draw the image
                        drawImage(
                            image = imageBitmap,
                            dstOffset = imageOffset.let { androidx.compose.ui.unit.IntOffset(it.x.toInt(), it.y.toInt()) },
                            dstSize = androidx.compose.ui.unit.IntSize(displaySize.width.toInt(), displaySize.height.toInt())
                        )

                        // Draw overlay (darken areas outside crop)
                        val cropRectDisplay = scaleCropRect(cropState.cropRect, scale, imageOffset)

                        // Top overlay
                        drawRect(
                            color = Color.Black.copy(alpha = 0.5f),
                            topLeft = imageOffset,
                            size = Size(displaySize.width, cropRectDisplay.top - imageOffset.y)
                        )

                        // Bottom overlay
                        drawRect(
                            color = Color.Black.copy(alpha = 0.5f),
                            topLeft = Offset(imageOffset.x, cropRectDisplay.bottom),
                            size = Size(displaySize.width, imageOffset.y + displaySize.height - cropRectDisplay.bottom)
                        )

                        // Left overlay
                        drawRect(
                            color = Color.Black.copy(alpha = 0.5f),
                            topLeft = Offset(imageOffset.x, cropRectDisplay.top),
                            size = Size(cropRectDisplay.left - imageOffset.x, cropRectDisplay.height)
                        )

                        // Right overlay
                        drawRect(
                            color = Color.Black.copy(alpha = 0.5f),
                            topLeft = Offset(cropRectDisplay.right, cropRectDisplay.top),
                            size = Size(imageOffset.x + displaySize.width - cropRectDisplay.right, cropRectDisplay.height)
                        )

                        // Draw crop rectangle border
                        drawRect(
                            color = Color.White,
                            topLeft = cropRectDisplay.topLeft,
                            size = cropRectDisplay.size,
                            style = Stroke(width = 2f)
                        )

                        // Draw corner handles
                        val handleSize = 20f
                        listOf(
                            cropRectDisplay.topLeft,
                            cropRectDisplay.topRight,
                            cropRectDisplay.bottomLeft,
                            cropRectDisplay.bottomRight
                        ).forEach { corner ->
                            drawCircle(
                                color = Color.White,
                                radius = handleSize / 2,
                                center = corner
                            )
                        }
                    }
                }
            }

            // Bottom buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
                ) {
                    Text("Cancel", color = Color.White)
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        // Perform the crop
                        val croppedImage = performCrop(image, cropState.cropRect)
                        croppedImage?.let { onCropComplete(it) }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007AFF))
                ) {
                    Text("Done", color = Color.White)
                }
            }
        }
    }
}

private fun calculateScaleToFit(imageSize: Size, containerSize: Size): Float {
    val scaleX = containerSize.width / imageSize.width
    val scaleY = containerSize.height / imageSize.height
    return minOf(scaleX, scaleY)
}

private fun scaleCropRect(cropRect: Rect, scale: Float, imageOffset: Offset): Rect {
    return Rect(
        offset = Offset(
            cropRect.left * scale + imageOffset.x,
            cropRect.top * scale + imageOffset.y
        ),
        size = Size(
            cropRect.width * scale,
            cropRect.height * scale
        )
    )
}

private fun isNearPoint(touch: Offset, point: Offset, radius: Float): Boolean {
    val dx = touch.x - point.x
    val dy = touch.y - point.y
    return (dx * dx + dy * dy) <= (radius * radius)
}

@OptIn(ExperimentalForeignApi::class)
private fun performCrop(image: UIImage, cropRect: Rect): UIImage? {
    val cgRect = CGRectMake(
        cropRect.left.toDouble(),
        cropRect.top.toDouble(),
        cropRect.width.toDouble(),
        cropRect.height.toDouble()
    )

    return image.crop(cgRect)
}