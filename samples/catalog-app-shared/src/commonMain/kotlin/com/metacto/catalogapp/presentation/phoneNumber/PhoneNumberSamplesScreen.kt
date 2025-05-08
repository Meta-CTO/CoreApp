package com.metacto.catalogapp.presentation.phoneNumber

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberSamplesContract.Event
import com.metacto.catalogapp.presentation.phoneNumber.components.PhoneNumberSamplesContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class PhoneNumberSamplesScreen : BaseScreen<PhoneNumberSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<PhoneNumberSamplesViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        PhoneNumberSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}