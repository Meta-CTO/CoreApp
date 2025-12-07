package com.metacto.catalogapp.presentation.sheetSamples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesContract.Event
import com.metacto.catalogapp.presentation.sheetSamples.components.SheetSamplesContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class SheetSamplesScreen : BaseScreen<SheetSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<SheetSamplesViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        SheetSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
