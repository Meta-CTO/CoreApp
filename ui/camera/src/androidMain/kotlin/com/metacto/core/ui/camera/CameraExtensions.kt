package com.metacto.core.ui.camera

import androidx.camera.core.CameraSelector
import com.metacto.core.ui.camera.models.CameraLens

internal fun CameraLens.toCameraXLensFacing(): Int {
    return when (this) {
        CameraLens.FRONT -> CameraSelector.LENS_FACING_FRONT
        CameraLens.BACK -> CameraSelector.LENS_FACING_BACK
        else -> CameraSelector.LENS_FACING_BACK
    }
}