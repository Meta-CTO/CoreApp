package com.metacto.core.utils.imagePreloader

val Class = IPreloader::class

interface IPreloader {
    fun preloadImages(vararg urls: String)
}