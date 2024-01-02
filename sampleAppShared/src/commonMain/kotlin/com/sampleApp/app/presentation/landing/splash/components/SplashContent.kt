package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import com.sampleApp.app.presentation.landing.splash.components.logo.SplashLogo

@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        SplashLogo()
    }
}