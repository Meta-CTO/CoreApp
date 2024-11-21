package com.sampleApp.app.presentation.testsheet2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sampleApp.app.presentation.testsheet2.TestSheet2Contract.Event
import com.sampleApp.app.presentation.testsheet2.components.TestSheet2Content
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel

internal class TestSheet2 : BaseSheet<TestSheet2ViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<TestSheet2ViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        TestSheet2Content(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
