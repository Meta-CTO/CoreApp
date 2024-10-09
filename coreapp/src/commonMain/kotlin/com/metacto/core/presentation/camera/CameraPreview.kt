package com.metacto.core.presentation.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.permissions.enums.Permission
import org.koin.compose.koinInject

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraController: CameraController
) {
    // Get main settings
    val permissionManager = koinInject<IPermissionManager>()
    var isCameraPermissionGranted by remember {
        mutableStateOf(false)
    }

    // Check camera permission
    LaunchedEffect(Unit) {
        // Update permission flags
        isCameraPermissionGranted = permissionManager.isPermissionGranted(Permission.CAMERA)

        // Request the permission if not granted
        if (isCameraPermissionGranted.not()) {
            try {
                permissionManager.grantPermission(Permission.CAMERA)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

        // Update permission flags
        isCameraPermissionGranted = permissionManager.isPermissionGranted(Permission.CAMERA)
    }

    // Render camera preview if possible
    if (isCameraPermissionGranted) {
        CameraPreviewView(
            modifier = modifier,
            cameraEngine = cameraController.cameraEngine
        )
    } else {
        // Render empty box
        Box(
            modifier = modifier
        )
    }
}