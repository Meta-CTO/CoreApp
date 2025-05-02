package com.metacto.catalogapp.presentation.notifications.components

import androidx.compose.runtime.Composable
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.State
import org.koin.compose.koinInject

@Composable
internal fun NotificationsSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Container column
    AppScreenColumn(
        title = "Notifications",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        // TODO: Render content
    }
}
