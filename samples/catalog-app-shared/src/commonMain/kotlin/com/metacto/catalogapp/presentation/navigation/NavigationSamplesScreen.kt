package com.metacto.catalogapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.catalogapp.presentation.navigation.NavigationSamplesContract.Event
import com.metacto.catalogapp.presentation.navigation.components.NavigationSamplesContent
import com.metacto.core.ui.base.rememberViewModel

internal class NavigationSamplesScreen : BaseScreen<NavigationSamplesViewModel>() {

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<NavigationSamplesViewModel>()

        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        NavigationSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}