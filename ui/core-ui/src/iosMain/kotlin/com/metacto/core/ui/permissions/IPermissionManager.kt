package com.metacto.core.ui.permissions

import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.enums.PermissionState

actual interface IPermissionManager {
    actual suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    )
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState
}
