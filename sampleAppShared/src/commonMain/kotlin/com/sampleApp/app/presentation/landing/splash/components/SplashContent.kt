package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import com.sampleApp.app.presentation.theme.AppTheme

@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val modifier = if (state.isWelcome) Modifier.background(Color.Blue) else Modifier

    ScreenColumn(
        isRefreshable = true,
        isRefreshing = false
    ) {

        Text(
            text = "Schedule repeating",
            style = AppTheme.typography.fenwickBold24,
            color = Color.Red,
            modifier = modifier
                .clickable { onEvent(Event.ScheduleRepeatingNotification) }
        )

        Text(
            text = "Cancel scheduled",
            style = AppTheme.typography.fenwickBold24,
            color = Color.Red,
            modifier = modifier
                .clickable { onEvent(Event.CancelScheduledNotification) }
        )
    }
}