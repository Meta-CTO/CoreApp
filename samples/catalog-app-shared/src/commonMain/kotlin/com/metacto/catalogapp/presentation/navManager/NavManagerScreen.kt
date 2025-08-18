package com.metacto.catalogapp.presentation.navManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.catalogapp.presentation.navManager.NavManagerContract.Event
import com.metacto.catalogapp.presentation.navManager.components.NavManagerContent
import com.metacto.core.ui.base.rememberViewModel

internal class NavManagerScreen : BaseScreen<NavManagerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<NavManagerViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        NavManagerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
