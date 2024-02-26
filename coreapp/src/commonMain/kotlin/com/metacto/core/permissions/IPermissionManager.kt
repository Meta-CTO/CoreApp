package com.metacto.core.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState

expect interface IPermissionManager {
    suspend fun requestPermission(permission: Permission)
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun getPermissionState(permission: Permission): PermissionState
    fun openAppSettings()
    suspend fun grantPermission(permission: Permission)
}
