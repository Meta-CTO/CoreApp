package com.metacto.core.ui.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController

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