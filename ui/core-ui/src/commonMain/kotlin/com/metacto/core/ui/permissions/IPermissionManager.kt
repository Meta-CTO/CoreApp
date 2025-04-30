package com.metacto.core.ui.permissions

import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.enums.PermissionState

expect interface IPermissionManager {
    suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean = true
    )
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun getPermissionState(permission: Permission): PermissionState
}
