package com.metacto.catalogapp.presentation.customDialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.Event
import com.metacto.catalogapp.presentation.customDialog.components.CustomDialogSamplesContent
import com.metacto.core.ui.base.rememberViewModel

internal class CustomDialogSamplesScreen : BaseScreen<CustomDialogSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<CustomDialogSamplesViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        CustomDialogSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
