package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun PermissionsController.requestPermission(permission: Permission) {
    when (getPlatformType()) {
        PlatformType.ANDROID -> requestAndroidPermission(permission)
        PlatformType.IOS -> requestIosPermission(permission)
    }
}

private suspend fun PermissionsController.requestAndroidPermission(permission: Permission) {
    // Check permission state
    when (getPermissionState(permission)) {
        PermissionState.NotDetermined -> try {
            providePermission(permission)
        } catch (_: DeniedAlwaysException) {
            navigateToAppSettings()
        }

        PermissionState.DeniedAlways -> navigateToAppSettings()

        else -> providePermission(permission)
    }
}

private suspend fun PermissionsController.requestIosPermission(permission: Permission) {
    try {
        providePermission(permission)
    } catch (_: DeniedAlwaysException) {
        openAppSettings()
    }
}

private suspend fun PermissionsController.navigateToAppSettings() = withContext(Dispatchers.Main) {
    openAppSettings()
}