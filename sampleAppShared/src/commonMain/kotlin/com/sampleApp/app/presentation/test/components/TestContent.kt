package com.sampleApp.app.presentation.test.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.toolbar.Toolbar
import com.sampleApp.app.presentation.test.TestContract.Event
import com.sampleApp.app.presentation.test.TestContract.State

@Composable
internal fun TestContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        toolbar = {
            Toolbar(
                showStartIcon = true,
                onStartIconClick = {
                    onEvent(Event.BackClicked)
                }
            )
        }
    ) {
        Text(
            text = "Test Screen"
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Clear and open MainScreen",
            onClick = {
                onEvent(Event.ClearAndOpenMainScreen)
            }
        )
    }
}
