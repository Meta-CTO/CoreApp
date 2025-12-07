package com.metacto.catalogapp.presentation.main.applepay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.main.applepay.ApplePayContract.Event
import com.metacto.catalogapp.presentation.main.applepay.components.ApplePayContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class ApplePayScreen : BaseScreen<ApplePayViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<ApplePayViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        ApplePayContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
