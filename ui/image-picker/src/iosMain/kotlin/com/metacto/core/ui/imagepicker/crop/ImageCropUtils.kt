package com.metacto.core.ui.imagepicker.crop

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage

/**
 * Crop a UIImage to the specified rectangle
 */
@OptIn(ExperimentalForeignApi::class)
fun UIImage.crop(rect: CValue<CGRect>): UIImage? {
    return rect.useContents {
        // Create a new context with the crop size
        UIGraphicsBeginImageContextWithOptions(
            size = CGSizeMake(size.width, size.height),
            opaque = false,
            scale = this@crop.scale
        )

        // Draw the image, adjusted for the crop rect
        this@crop.drawAtPoint(
            point = CGPointMake(-origin.x, -origin.y)
        )

        // Get the cropped image from the context
        val croppedImage = UIGraphicsGetImageFromCurrentImageContext()

        // Clean up
        UIGraphicsEndImageContext()

        croppedImage
    }
}

/**
 * Scale a UIImage to fit within maxSize while maintaining aspect ratio
 */
@OptIn(ExperimentalForeignApi::class)
fun UIImage.scaleToFit(maxSize: CValue<CGSize>): UIImage? {
    val (maxWidth, maxHeight) = maxSize.useContents { width to height }
    val (imgWidth, imgHeight) = this.size.useContents { width to height }

    val widthRatio = maxWidth / imgWidth
    val heightRatio = maxHeight / imgHeight
    val scaleFactor = minOf(widthRatio, heightRatio)

    if (scaleFactor >= 1.0) return this // No need to scale down

    val newWidth = imgWidth * scaleFactor
    val newHeight = imgHeight * scaleFactor
    val newSize = CGSizeMake(newWidth, newHeight)

    UIGraphicsBeginImageContextWithOptions(
        size = newSize,
        opaque = false,
        scale = 0.0
    )

    this.drawInRect(CGRectMake(0.0, 0.0, newWidth, newHeight))
    val scaledImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return scaledImage
}

/**
 * Calculate crop rect in image coordinates from display coordinates
 */
@OptIn(ExperimentalForeignApi::class)
fun calculateImageCropRect(
    imageSize: CValue<CGSize>,
    displaySize: CValue<CGSize>,
    cropRect: CValue<CGRect>,
    scale: Double
): CValue<CGRect> {
    val (imgWidth, imgHeight) = imageSize.useContents { width to height }
    val (displayWidth, displayHeight) = displaySize.useContents { width to height }

    // Calculate the scale factor between display and actual image
    val scaleX = imgWidth / displayWidth
    val scaleY = imgHeight / displayHeight

    return cropRect.useContents {
        CGRectMake(
            origin.x * scaleX,
            origin.y * scaleY,
            size.width * scaleX,
            size.height * scaleY
        )
    }
}
