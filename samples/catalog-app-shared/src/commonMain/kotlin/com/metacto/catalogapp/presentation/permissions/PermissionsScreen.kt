package com.metacto.catalogapp.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.permissions.PermissionsContract.Event
import com.metacto.catalogapp.presentation.permissions.components.PermissionsContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class PermissionsScreen : BaseScreen<PermissionsViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<PermissionsViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        PermissionsContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
