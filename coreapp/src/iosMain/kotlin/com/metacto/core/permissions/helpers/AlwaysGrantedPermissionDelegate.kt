package com.metacto.core.permissions.helpers

import com.metacto.core.permissions.enums.PermissionState

class AlwaysGrantedPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() = Unit

    override suspend fun getPermissionState() = PermissionState.Granted
    override fun openSettingPage() = Unit
}
