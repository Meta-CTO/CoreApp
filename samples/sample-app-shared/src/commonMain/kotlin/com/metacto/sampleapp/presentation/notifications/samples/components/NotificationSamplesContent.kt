package com.metacto.sampleapp.presentation.notifications.samples.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.navigation.NavManager
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesContract.State
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesContract.Event
import org.koin.compose.koinInject

@Composable
internal fun NotificationSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        isScrollable = true,
        toolbar = {
            PrimaryFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Go back",
                onClick = {
                    navManager.goBack()
                }
            )
        }
    ) {

    }
}
