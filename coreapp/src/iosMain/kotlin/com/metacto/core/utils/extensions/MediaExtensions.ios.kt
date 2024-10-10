package com.metacto.core.utils.extensions

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
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