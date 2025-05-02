package com.metacto.sampleapp.presentation.notifications.samples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesContract.Event
import com.metacto.sampleapp.presentation.notifications.samples.components.NotificationSamplesContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class NotificationSamplesScreen : BaseScreen<NotificationSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<NotificationSamplesViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        NotificationSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
