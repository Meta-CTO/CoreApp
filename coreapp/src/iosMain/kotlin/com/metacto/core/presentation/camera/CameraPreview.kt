package com.metacto.core.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraPreview(
    modifier: Modifier,
    cameraController: CameraController
) {
    UIKitViewController(
        factory = { cameraController },
        modifier = modifier
    )
}