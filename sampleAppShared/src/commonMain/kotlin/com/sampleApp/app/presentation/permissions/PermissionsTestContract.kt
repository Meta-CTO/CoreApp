package com.sampleApp.app.presentation.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState


class PermissionsTestContract {

    data class State(
        val isInitialized: Boolean = false,
        val permissionStates: Map<Permission, PermissionState> = emptyMap(),
        val isLoading: Boolean = false,
        val lastResult: String = "",
        val testResults: List<TestResult> = emptyList()
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object RefreshAllPermissions : Event()
        data class RequestPermission(val permission: Permission, val openAppSettings: Boolean = true) : Event()
        data class CheckPermissionState(val permission: Permission) : Event()
        data object ClearResults : Event()
        data object TestAllPermissions : Event()
        data object BackClicked : Event()
    }

    sealed class Effect : ViewSideEffect

    data class TestResult(
        val permission: Permission,
        val action: String,
        val result: String,
    )
}