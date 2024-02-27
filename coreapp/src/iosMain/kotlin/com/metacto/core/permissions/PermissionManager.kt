package com.metacto.core.permissions

import com.metacto.core.permissions.helpers.enums.Permission
import com.metacto.core.permissions.helpers.enums.PermissionState
import com.metacto.core.permissions.helpers.exceptions.DeniedAlwaysException
import com.metacto.core.permissions.helpers.PermissionDelegate
import com.metacto.core.permissions.helpers.IPermissionDelegateFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class PermissionManager(
    private val delegateFactory: IPermissionDelegateFactory
) : IPermissionManager {

    override suspend fun requestPermission(permission: Permission) {
        return getDelegate(permission).providePermission()
    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean {
        return getDelegate(permission).getPermissionState() == PermissionState.Granted
    }

    override suspend fun getPermissionState(permission: Permission): PermissionState {
        return getDelegate(permission).getPermissionState()
    }

    override fun openAppSettings() {
        val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)!!
        UIApplication.sharedApplication.openURL(settingsUrl)
    }

    override suspend fun grantPermission(permission: Permission) {
        try {
            requestPermission(permission)
        } catch (_: DeniedAlwaysException) {
            withContext(Dispatchers.Main) {
                openAppSettings()
            }
        }
    }

    private fun getDelegate(permission: Permission): PermissionDelegate {
        return delegateFactory.getDelegate(permission)
    }
}
