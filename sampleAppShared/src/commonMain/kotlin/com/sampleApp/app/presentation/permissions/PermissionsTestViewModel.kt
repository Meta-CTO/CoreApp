package com.sampleApp.app.presentation.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.permissions.exceptions.DeniedException
import com.metacto.core.permissions.exceptions.RequestCanceledException
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.permissions.PermissionsTestContract.Event
import com.sampleApp.app.presentation.permissions.PermissionsTestContract.State
import com.sampleApp.app.presentation.permissions.PermissionsTestContract.Effect
import com.sampleApp.app.presentation.permissions.PermissionsTestContract.TestResult
import kotlinx.coroutines.delay

class PermissionsTestViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.RefreshAllPermissions -> refreshAllPermissions()
        is Event.RequestPermission -> requestPermission(event.permission, event.openAppSettings)
        is Event.CheckPermissionState -> checkPermissionState(event.permission)
        Event.ClearResults -> clearResults()
        Event.TestAllPermissions -> testAllPermissions()
        Event.BackClicked -> navManager.goBack()
    }

    private fun init() {
        if (currentState.isInitialized) return
        setState { copy(isInitialized = true) }
        refreshAllPermissions()
    }

    private fun refreshAllPermissions() = executeSilent({
        setState { copy(isLoading = true) }

        val permissionStates = mutableMapOf<Permission, PermissionState>()

        Permission.entries.forEach { permission ->
            try {
                val state = permissionManager.getPermissionState(permission)
                permissionStates[permission] = state
                delay(50)
            } catch (e: Exception) {
                permissionStates[permission] = PermissionState.NotDetermined
                addTestResult(permission, "Get State", "Error: ${e.message}")
            }
        }

        setState {
            copy(
                permissionStates = permissionStates,
                isLoading = false
            )
        }

        globalState.snackBar(
            SnackBarParams(
                message = "Refreshed all permission states",
                type = SnackBarType.SUCCESS
            )
        )
    })

    private fun requestPermission(permission: Permission, openAppSettings: Boolean) = executeSilent({
        setState { copy(isLoading = true) }

        try {
            permissionManager.requestPermission(permission, openAppSettings)

            val newState = permissionManager.getPermissionState(permission)
            val updatedStates = currentState.permissionStates.toMutableMap()
            updatedStates[permission] = newState

            setState {
                copy(
                    permissionStates = updatedStates,
                    lastResult = "✅ Permission granted",
                    isLoading = false
                )
            }

            addTestResult(permission, "Request (settings: $openAppSettings)", "Granted")

            globalState.snackBar(
                SnackBarParams(
                    message = "✅ ${permission.name} permission granted",
                    type = SnackBarType.SUCCESS
                )
            )

        } catch (e: DeniedAlwaysException) {
            val updatedStates = currentState.permissionStates.toMutableMap()
            updatedStates[permission] = PermissionState.DeniedAlways

            setState {
                copy(
                    permissionStates = updatedStates,
                    lastResult = "❌ Permission denied always",
                    isLoading = false
                )
            }

            addTestResult(permission, "Request (settings: $openAppSettings)", "Denied Always")

            globalState.snackBar(
                SnackBarParams(
                    message = "❌ ${permission.name} permission denied always",
                    type = SnackBarType.ERROR
                )
            )

        } catch (e: DeniedException) {
            val updatedStates = currentState.permissionStates.toMutableMap()
            updatedStates[permission] = PermissionState.Denied

            setState {
                copy(
                    permissionStates = updatedStates,
                    lastResult = "⚠️ Permission denied",
                    isLoading = false
                )
            }

            addTestResult(permission, "Request (settings: $openAppSettings)", "Denied")

            globalState.snackBar(
                SnackBarParams(
                    message = "⚠️ ${permission.name} permission denied",
                    type = SnackBarType.ERROR
                )
            )

        } catch (e: RequestCanceledException) {
            setState {
                copy(
                    lastResult = "❌ Request cancelled",
                    isLoading = false
                )
            }

            addTestResult(permission, "Request (settings: $openAppSettings)", "Cancelled")

            globalState.snackBar(
                SnackBarParams(
                    message = "❌ ${permission.name} request cancelled",
                    type = SnackBarType.ERROR
                )
            )

        } catch (e: Exception) {
            setState {
                copy(
                    lastResult = "❌ Error: ${e.message}",
                    isLoading = false
                )
            }

            addTestResult(permission, "Request (settings: $openAppSettings)", "Error: ${e.message}")

            globalState.snackBar(
                SnackBarParams(
                    message = "❌ Error requesting ${permission.name}: ${e.message}",
                    type = SnackBarType.ERROR
                )
            )
        }
    })

    private fun checkPermissionState(permission: Permission) = executeSilent({
        val state = permissionManager.getPermissionState(permission)
        val isGranted = permissionManager.isPermissionGranted(permission)

        val updatedStates = currentState.permissionStates.toMutableMap()
        updatedStates[permission] = state

        setState {
            copy(
                permissionStates = updatedStates,
                lastResult = "State: $state, Granted: $isGranted"
            )
        }

        addTestResult(permission, "Check State", "State: $state, Granted: $isGranted")
    })

    private fun testAllPermissions() = executeSilent({
        setState { copy(isLoading = true) }

        Permission.entries.forEach { permission ->
            try {
                val state = permissionManager.getPermissionState(permission)
                val isGranted = permissionManager.isPermissionGranted(permission)

                addTestResult(
                    permission,
                    "Auto Test",
                    "State: $state, Granted: $isGranted"
                )

                delay(100)
            } catch (e: Exception) {
                addTestResult(permission, "Auto Test", "Error: ${e.message}")
            }
        }

        setState { copy(isLoading = false) }

        globalState.snackBar(
            SnackBarParams(
                message = "Completed testing all permissions",
                type = SnackBarType.SUCCESS
            )
        )
    })

    private fun clearResults() {
        setState {
            copy(
                testResults = emptyList(),
                lastResult = ""
            )
        }
    }

    private fun addTestResult(permission: Permission, action: String, result: String) {
        val newResult = TestResult(permission, action, result)
        val updatedResults = currentState.testResults + newResult
        setState { copy(testResults = updatedResults) }
    }
}