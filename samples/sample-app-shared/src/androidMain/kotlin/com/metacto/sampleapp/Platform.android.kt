package com.metacto.sampleapp

import android.os.Build
import com.metacto.sampleapp.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()