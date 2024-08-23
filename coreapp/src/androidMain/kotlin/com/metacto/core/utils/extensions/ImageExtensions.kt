package com.metacto.core.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

suspend fun String.downloadBitmap(context: Context): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .allowHardware(false)
        .build()

    return when (val result = loader.execute(request)) {
        is SuccessResult -> result.drawable.toBitmap()
        else -> null
    }
}