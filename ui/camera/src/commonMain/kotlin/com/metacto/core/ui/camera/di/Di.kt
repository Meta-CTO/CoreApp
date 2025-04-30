package com.metacto.core.ui.camera.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun cameraModule() = module {
    // Common dependencies can be added here

    includes(platformModule())
}

internal expect fun platformModule(): Module