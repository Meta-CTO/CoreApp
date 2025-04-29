package com.metacto.core.extensions

// TODO: remove this
//import android.content.Context
//import android.graphics.Bitmap
//import coil3.ImageLoader
//import coil3.request.ImageRequest
//import coil3.request.SuccessResult
//import coil3.request.allowHardware
//import coil3.toBitmap
//
//suspend fun String.downloadBitmap(context: Context): Bitmap? {
//    val loader = ImageLoader(context)
//    val request = ImageRequest.Builder(context)
//        .data(this)
//        .allowHardware(false)
//        .build()
//
//    return when (val result = loader.execute(request)) {
//        is SuccessResult -> result.image.toBitmap()
//        else -> null
//    }
//}