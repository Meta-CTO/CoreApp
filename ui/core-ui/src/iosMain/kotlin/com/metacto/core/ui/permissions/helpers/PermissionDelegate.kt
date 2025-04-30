package com.metacto.core.ui.permissions.helpers

import com.metacto.core.ui.permissions.enums.PermissionState

interface PermissionDelegate {
    suspend fun providePermission()
    suspend fun getPermissionState(): PermissionState
}
