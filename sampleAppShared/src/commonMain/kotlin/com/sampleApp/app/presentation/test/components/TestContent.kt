package com.sampleApp.app.presentation.test.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.navigation.NavManager
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.toolbar.Toolbar
import com.metacto.core.presentation.youtube.YoutubeContract
import com.metacto.core.presentation.youtube.YoutubeScreen
import com.sampleApp.app.presentation.main.MainScreen
import com.sampleApp.app.presentation.test.TestContract.Event
import com.sampleApp.app.presentation.test.TestContract.State
import com.sampleApp.app.presentation.test.TestScreen
import org.koin.compose.koinInject

@Composable
internal fun TestContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val navManager = koinInject<NavManager>()

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
            text = "Navigate and pop to MainScreen",
            onClick = {
                navManager.navigateAndPopToExclusive(
                    navToDest = YoutubeScreen("Gmhk7mWG050"),
                    popToDestClass = MainScreen::class
                )
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Clear and open MainScreen",
            onClick = {
                onEvent(Event.ClearAndOpenMainScreen)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Check main screen",
            onClick = {
                onEvent(Event.CheckMainScreenClicked)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Check youtube screen",
            onClick = {
                onEvent(Event.CheckYoutubeScreenClicked)
            }
        )
    }
}
