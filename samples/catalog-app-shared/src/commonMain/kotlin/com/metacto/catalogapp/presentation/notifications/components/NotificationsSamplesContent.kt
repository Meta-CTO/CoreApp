package com.metacto.catalogapp.presentation.notifications.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.State
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
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
        // Button to request notification permission
        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Enable Push Notifications",
            onClick = {
                onEvent(Event.EnablePushNotifications)
            }
        )
    }
}
