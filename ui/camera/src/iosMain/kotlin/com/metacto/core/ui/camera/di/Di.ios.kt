package com.metacto.core.ui.camera.di

import com.metacto.core.ui.camera.CameraController
import com.metacto.core.ui.camera.CameraEngine
import com.metacto.core.ui.camera.models.CameraLens
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add iOS specific dependencies here

    factory { (defaultCamera: CameraLens?) ->
        val cameraEngine = CameraEngine(
            defaultCamera = defaultCamera ?: CameraLens.BACK
        )
        CameraController(
            permissionManager = get(),
            cameraEngine = cameraEngine
        )
    }
}