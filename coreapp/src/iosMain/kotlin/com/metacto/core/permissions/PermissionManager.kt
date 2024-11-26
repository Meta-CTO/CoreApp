package com.metacto.core.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.permissions.helpers.IPermissionDelegateFactory
import com.metacto.core.permissions.helpers.PermissionDelegate
import com.metacto.core.utils.extensions.openAppSettings

class PermissionManager(
    private val delegateFactory: IPermissionDelegateFactory
) : IPermissionManager {

    override suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    ) = when {
        openAppSettingsIfRequired -> handlePermissionRequestWithSettings(permission)
        else -> handlePermissionRequest(permission)
    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean {
        return getPermissionState(permission) == PermissionState.Granted
    }

    override suspend fun getPermissionState(permission: Permission): PermissionState {
        return getDelegate(permission).getPermissionState()
    }

    private suspend fun handlePermissionRequest(permission: Permission) {
        getDelegate(permission).providePermission()
    }

    private suspend fun handlePermissionRequestWithSettings(permission: Permission) {
        val initialState = getPermissionState(permission)
        try {
            handlePermissionRequest(permission)
        } catch (exception: DeniedAlwaysException) {
            if (initialState == PermissionState.DeniedAlways) {
                openAppSettings()
            }
            throw exception
        }
    }

    private fun getDelegate(permission: Permission): PermissionDelegate {
         return delegateFactory.getDelegate(permission)
    }
}