package com.metacto.catalogapp.presentation.datePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.catalogapp.presentation.datePicker.DatePickerContract.Event
import com.metacto.catalogapp.presentation.datePicker.components.DatePickerContent
import com.metacto.core.ui.base.rememberViewModel

internal class DatePickerScreen : BaseScreen<DatePickerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<DatePickerViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        DatePickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
