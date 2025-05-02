package com.metacto.sampleapp.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.navigation.NavManager
import com.metacto.sampleapp.presentation.main.MainContract.Event
import com.metacto.sampleapp.presentation.main.MainContract.State
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesScreen
import org.koin.compose.koinInject

@Composable
internal fun MainContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManage = koinInject<NavManager>()

    ScreenColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        isScrollable = true,
        modifier = Modifier.fillMaxSize()
    ) {
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Notification samples",
            onClick = {
                navManage.navigate(NotificationSamplesScreen())
            }
        )
    }
}