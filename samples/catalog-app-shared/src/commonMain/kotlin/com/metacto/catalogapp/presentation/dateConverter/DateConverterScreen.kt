package com.metacto.catalogapp.presentation.dateConverter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.dateConverter.DateConverterContract.Event
import com.metacto.catalogapp.presentation.dateConverter.components.DateConverterContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class DateConverterScreen : BaseScreen<DateConverterViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<DateConverterViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        DateConverterContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
