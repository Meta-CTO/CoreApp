package com.metacto.core.presentation.camera

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal actual fun CameraPreviewView(
    modifier: Modifier,
    cameraEngine: CameraEngine
) {
    // Get main objects
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Init camera controller
    LaunchedEffect(Unit) {
        cameraEngine.init(lifecycleOwner)
    }

    // Create preview view
    val previewView = remember {
        PreviewView(context)
    }

    // Render preview view
    AndroidView(
        factory = { previewView },
        modifier = modifier
    )

    // Start the camera
    LaunchedEffect(Unit) {
        cameraEngine.startCamera(previewView)
    }
}