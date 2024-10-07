package com.metacto.core.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun CameraPreviewView(
    modifier: Modifier = Modifier,
    cameraEngine: CameraEngine
)