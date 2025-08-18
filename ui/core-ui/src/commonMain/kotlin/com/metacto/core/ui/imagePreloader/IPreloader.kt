package com.metacto.core.ui.imagePreloader

val Class = IPreloader::class

interface IPreloader {
    fun preloadImages(vararg urls: String)
}