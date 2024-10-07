package com.metacto.core.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun CameraPreviewView(
    modifier: Modifier,
    cameraEngine: CameraEngine
) {
    UIKitViewController(
        factory = { cameraEngine },
        modifier = modifier
    )
}