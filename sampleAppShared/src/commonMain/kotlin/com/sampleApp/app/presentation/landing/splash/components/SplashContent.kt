package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        topPadding = AppTheme.spacings.spacing0,
        bottomPadding = AppTheme.spacings.spacing0,
        startPadding = AppTheme.spacings.spacing0,
        endPadding = AppTheme.spacings.spacing0,
        enableSafeInsets = false
    ) {
        Text(
            text = if (state.isWelcome) "Welcome" else "Sample App",
            style = AppTheme.typography.fenwickBold24,
            color = AppTheme.colors.midnight,
            modifier = modifier
                .fillMaxSize()
                .clickable { onEvent(Event.TextClicked) }
        )
    }
}