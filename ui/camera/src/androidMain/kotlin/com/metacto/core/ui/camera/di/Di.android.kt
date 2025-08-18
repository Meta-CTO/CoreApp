package com.metacto.core.ui.camera.di

import com.metacto.core.ui.camera.CameraController
import com.metacto.core.ui.camera.models.CameraLens
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add Android specific dependencies here

    factory { (defaultCamera: CameraLens?) ->
        val cameraEngine = com.metacto.core.ui.camera.CameraEngine(
            context = androidContext(),
            defaultCamera = defaultCamera ?: CameraLens.BACK
        )
        CameraController(
            permissionManager = get(),
            cameraEngine = cameraEngine
        )
    }
}