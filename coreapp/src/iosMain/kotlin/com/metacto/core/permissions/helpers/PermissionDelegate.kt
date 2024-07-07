package com.metacto.core.permissions.helpers

import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.utils.extensions.openAppSettingsPage

interface PermissionDelegate {
    suspend fun providePermission()

    suspend fun getPermissionState(): PermissionState

    fun openSettingPage() {
        openAppSettingsPage()
    }
}
