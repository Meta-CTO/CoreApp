package com.metacto.catalogapp.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.catalogapp.presentation.main.MainContract.Event
import com.metacto.catalogapp.presentation.main.components.MainContent

internal class MainScreen : BaseScreen<MainViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MainViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        MainContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}