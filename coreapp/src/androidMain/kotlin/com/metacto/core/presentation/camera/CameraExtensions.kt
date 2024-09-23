package com.metacto.core.presentation.camera

import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import com.metacto.core.presentation.camera.models.CameraFlashMode
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.CameraRotation

internal fun CameraFlashMode.toCameraXFlashMode(): Int {
    return when (this) {
        CameraFlashMode.ON -> ImageCapture.FLASH_MODE_ON
        CameraFlashMode.OFF -> ImageCapture.FLASH_MODE_OFF
    }
}

internal fun CameraLens.toCameraXLensFacing(): Int {
    return when (this) {
        CameraLens.FRONT -> CameraSelector.LENS_FACING_FRONT
        CameraLens.BACK -> CameraSelector.LENS_FACING_BACK
        else -> CameraSelector.LENS_FACING_BACK
    }
}

internal fun CameraRotation.toSurfaceRotation(): Int {
    return when (this) {
        CameraRotation.ROTATION_0 -> Surface.ROTATION_0
        CameraRotation.ROTATION_90 -> Surface.ROTATION_90
        CameraRotation.ROTATION_180 -> Surface.ROTATION_180
        CameraRotation.ROTATION_270 -> Surface.ROTATION_270
    }
}