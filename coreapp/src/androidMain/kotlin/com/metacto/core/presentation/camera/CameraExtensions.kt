package com.metacto.core.presentation.camera

import androidx.camera.core.CameraSelector
import com.metacto.core.presentation.camera.models.CameraLens

internal fun CameraLens.toCameraXLensFacing(): Int {
    return when (this) {
        CameraLens.FRONT -> CameraSelector.LENS_FACING_FRONT
        CameraLens.BACK -> CameraSelector.LENS_FACING_BACK
        else -> CameraSelector.LENS_FACING_BACK
    }
}