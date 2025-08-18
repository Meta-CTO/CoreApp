package com.metacto.core.ui.imagePreloader

import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.CachePolicy
import coil3.request.ImageRequest

class Preloader(
    private val context: PlatformContext
) : IPreloader {

    override fun preloadImages(vararg urls: String) {
        urls.forEach {
            preLoadImage(it)
        }
    }

    private fun preLoadImage(url: String) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()

        SingletonImageLoader.get(context).enqueue(request)
    }
}