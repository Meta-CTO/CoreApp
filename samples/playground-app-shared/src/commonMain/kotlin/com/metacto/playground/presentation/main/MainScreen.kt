package com.metacto.playground.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.playground.presentation.main.MainContract.Event
import com.metacto.playground.presentation.main.components.MainContent

internal class MainScreen : CoreScreen<MainViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MainViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        MainContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}