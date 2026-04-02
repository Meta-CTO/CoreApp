package com.sampleApp.app.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.permissions.BindEffect
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel

import com.sampleApp.app.presentation.permissions.PermissionsTestContract.Event
import org.koin.compose.koinInject

internal object PermissionsTestScreen : BaseScreen<PermissionsTestViewModel>() {
    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<PermissionsTestViewModel>()
        val permissionManager = koinInject<IPermissionManager>()
        val state = viewModel.viewState.value

        BindEffect(permissionManager)

        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        PermissionsTestContent(
            permissionStates = state.permissionStates,
            isLoading = state.isLoading,
            onBackClicked = { viewModel.setEvent(Event.BackClicked) },
            onRequestPermission = { permission, openSettings ->
                viewModel.setEvent(Event.RequestPermission(permission, openSettings))
            }
        )
    }
}