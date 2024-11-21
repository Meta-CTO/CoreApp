package com.sampleApp.app.presentation.testsheet1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sampleApp.app.presentation.testsheet1.TestSheet1Contract.Event
import com.sampleApp.app.presentation.testsheet1.components.TestSheet1Content
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel

internal class TestSheet1 : BaseSheet<TestSheet1ViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<TestSheet1ViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        TestSheet1Content(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
