package com.sampleApp.app.presentation.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sampleApp.app.presentation.test.TestContract.Event
import com.sampleApp.app.presentation.test.components.TestContent
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel

internal object TestScreen : BaseScreen<TestViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<TestViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        TestContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
