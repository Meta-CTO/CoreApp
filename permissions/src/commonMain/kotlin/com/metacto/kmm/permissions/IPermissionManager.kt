package com.metacto.kmm.permissions

import com.metacto.kmm.permissions.enums.Permission
import com.metacto.kmm.permissions.enums.PermissionState


expect interface IPermissionManager {
    suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean = true
    )
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun getPermissionState(permission: Permission): PermissionState
}
