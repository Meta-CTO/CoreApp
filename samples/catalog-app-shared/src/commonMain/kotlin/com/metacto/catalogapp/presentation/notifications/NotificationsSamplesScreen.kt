package com.metacto.catalogapp.presentation.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.catalogapp.presentation.notifications.components.NotificationsSamplesContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class NotificationsSamplesScreen : BaseScreen<NotificationsSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<NotificationsSamplesViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        NotificationsSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
