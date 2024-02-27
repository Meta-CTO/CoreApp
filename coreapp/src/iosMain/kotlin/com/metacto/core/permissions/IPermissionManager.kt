package com.metacto.core.permissions

import com.metacto.core.permissions.helpers.enums.Permission
import com.metacto.core.permissions.helpers.enums.PermissionState

actual interface IPermissionManager {
    actual suspend fun requestPermission(permission: Permission)
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState
    actual fun openAppSettings()
    actual suspend fun grantPermission(permission: Permission)
}
