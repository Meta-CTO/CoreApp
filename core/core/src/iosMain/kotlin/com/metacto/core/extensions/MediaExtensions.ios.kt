package com.metacto.core.extensions

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGContextDrawImage
import platform.CoreGraphics.CGContextRotateCTM
import platform.CoreGraphics.CGContextTranslateCTM
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImageOrientation
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
fun UIImage.normalizedImage(): UIImage {
    if (this.imageOrientation == UIImageOrientation.UIImageOrientationUp) return this

    val size = this.size
    var width = 0.0
    var height = 0.0
    size.useContents {
        width = this.width
        height = this.height
    }
    val scale = this.scale

    UIGraphicsBeginImageContextWithOptions(size, false, scale)
    this.drawInRect(CGRectMake(0.0, 0.0, width, height))
    val normalizedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return normalizedImage ?: this
}

@OptIn(ExperimentalForeignApi::class)
fun UIImage.toByteArray(): ByteArray? {
    val imageNsData = UIImageJPEGRepresentation(
        image = this,
        compressionQuality = 1.0
    ) ?: return null

    return ByteArray(imageNsData.length.toInt()).apply {
        memcpy(this.refTo(0), imageNsData.bytes, imageNsData.length)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun UIImage.rotate(degrees: Double): UIImage {
    val radians = degrees * kotlin.math.PI / 180

    // Prepare the original size
    val originalSize = this.size
    var width = 0.0
    var height = 0.0
    originalSize.useContents {
        width = this.width
        height = this.height
    }

    // Calculate the new size after rotation
    val newSize = if (degrees == 90.0 || degrees == 270.0) {
        CGSizeMake(height, width) // Switch width and height for 90 and 270 degree rotation
    } else {
        CGSizeMake(width, height) // No switch needed for 180 or 0 degree rotation
    }

    // Prepare the new width and height
    var newWidth = 0.0
    var newHeight = 0.0
    newSize.useContents {
        newWidth = this.width
        newHeight = this.height
    }

    // Create a new image context with the calculated size
    UIGraphicsBeginImageContextWithOptions(newSize, false, this.scale)
    UIGraphicsGetCurrentContext()?.apply {
        CGContextTranslateCTM(this, newWidth / 2, newHeight / 2)
        CGContextRotateCTM(this, radians)
        CGContextDrawImage(this, CGRectMake(-width / 2, -height / 2, width, height), this@rotate.CGImage)
    }

    // Get the rotated image from the current context
    val rotatedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return rotatedImage ?: this
}