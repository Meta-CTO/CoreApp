package com.metacto.core.ui.permissions.helpers

import com.metacto.core.ui.permissions.enums.PermissionState

class AlwaysGrantedPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() = Unit
    override suspend fun getPermissionState() = PermissionState.Granted
}
