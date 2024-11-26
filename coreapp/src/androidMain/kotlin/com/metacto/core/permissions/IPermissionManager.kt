package com.metacto.core.permissions

import androidx.activity.ComponentActivity
import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState

actual interface IPermissionManager {
    actual suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    )
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState
    fun bind(activity: ComponentActivity)
}
