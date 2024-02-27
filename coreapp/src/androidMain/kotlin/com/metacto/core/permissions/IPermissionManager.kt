package com.metacto.core.permissions

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState

actual interface IPermissionManager {
    actual suspend fun requestPermission(permission: Permission)
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState
    actual fun openAppSettings()
    actual suspend fun grantPermission(permission: Permission)
    fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager)
}
