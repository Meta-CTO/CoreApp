package com.metacto.core.ui.imagepicker

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.getBytes

private object MediaTypeConstants {
    const val PUBLIC_IMAGE = "public.image"
    const val PUBLIC_MOVIE = "public.movie"
    const val PUBLIC_VIDEO = "public.video"
}

internal fun List<MediaType>.toMediaTypes(): List<String> {
    val mediaTypesList = mutableListOf<String>()
    
    if (this.contains(MediaType.Image)) {
        mediaTypesList.add(MediaTypeConstants.PUBLIC_IMAGE)
    }
    
    if (this.contains(MediaType.Video)) {
        mediaTypesList.add(MediaTypeConstants.PUBLIC_MOVIE)
        mediaTypesList.add(MediaTypeConstants.PUBLIC_VIDEO)
    }
    
    return mediaTypesList
}

internal fun String?.isVideoType(): Boolean {
    return this == MediaTypeConstants.PUBLIC_MOVIE || this == MediaTypeConstants.PUBLIC_VIDEO
}

internal fun NSURL.safePathString(): String {
    return this.path.orEmpty()
}

internal fun Map<Any?, *>.extractUIImage(allowEditing: Boolean): platform.UIKit.UIImage? {
    return if (allowEditing) {
        this["UIImagePickerControllerEditedImage"] as? platform.UIKit.UIImage
            ?: this["UIImagePickerControllerOriginalImage"] as? platform.UIKit.UIImage
    } else {
        this["UIImagePickerControllerOriginalImage"] as? platform.UIKit.UIImage
    }
}

internal fun Map<Any?, *>.extractVideoURL(): NSURL? {
    return this["UIImagePickerControllerMediaURL"] as? NSURL
}

internal fun Map<Any?, *>.extractMediaType(): String? {
    return this["UIImagePickerControllerMediaType"] as? String
}

/**
 * Extracts video data from NSURL
 */
@OptIn(ExperimentalForeignApi::class)
internal fun NSURL.extractVideoData(): ByteArray? {
    return try {
        val data = NSData.dataWithContentsOfURL(this) ?: return null
        val length = data.length.toInt()
        
        if (length == 0) return null
        
        val byteArray = ByteArray(length)
        byteArray.usePinned { pinned ->
            data.getBytes(pinned.addressOf(0), length.toULong())
        }
        byteArray
    } catch (e: Exception) {
        null
    }
}