package com.sampleApp.app.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.permissions.BindEffect
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel
import com.sampleApp.app.presentation.main.components.MainContent

internal class MainScreen : BaseScreen<MainViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MainViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.init()
        }

        // Binds the permissions controller to the LocalLifecycleOwner lifecycle.
        BindEffect(viewModel.permissionManager)

        // Render content
        MainContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}