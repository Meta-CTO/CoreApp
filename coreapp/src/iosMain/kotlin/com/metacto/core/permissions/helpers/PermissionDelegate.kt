package com.metacto.core.permissions.helpers

import com.metacto.core.permissions.helpers.enums.PermissionState

interface PermissionDelegate {
    suspend fun providePermission()
    suspend fun getPermissionState(): PermissionState
}
