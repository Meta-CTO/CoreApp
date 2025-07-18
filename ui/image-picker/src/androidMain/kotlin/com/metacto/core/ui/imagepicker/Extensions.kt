package com.metacto.core.ui.imagepicker

import android.app.Activity
import android.net.Uri

internal fun List<MediaType>.mimeTypes() = map { it.mimeType }

internal fun Uri.getMediaType(activity: Activity): MediaType {
    val mimeType = activity.contentResolver.getType(this)
    return when {
        mimeType?.startsWith("image/") == true -> MediaType.Image
        mimeType?.startsWith("video/") == true -> MediaType.Video
        else -> MediaType.Image
    }
}