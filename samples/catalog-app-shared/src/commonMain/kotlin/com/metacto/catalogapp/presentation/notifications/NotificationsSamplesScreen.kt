package com.metacto.catalogapp.presentation.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.catalogapp.presentation.notifications.components.NotificationsSamplesContent
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class NotificationsSamplesScreen : CoreScreen<NotificationsSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<NotificationsSamplesViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        NotificationsSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
