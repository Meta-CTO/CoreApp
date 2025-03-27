package com.sampleApp.app.presentation.test2.test2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sampleApp.app.presentation.test2.test2.Test2Contract.Event
import com.sampleApp.app.presentation.test2.test2.components.Test2Content
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel

internal class Test2Screen : BaseScreen<Test2ViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<Test2ViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        Test2Content(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
