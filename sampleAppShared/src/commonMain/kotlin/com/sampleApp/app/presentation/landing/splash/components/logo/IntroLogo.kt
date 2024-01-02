package com.sampleApp.app.presentation.landing.splash.components.logo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sampleApp.app.presentation.theme.AppTheme

@Composable
internal fun SplashLogo(
    modifier: Modifier = Modifier
) {
    // Container column
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.spacing8),
        modifier = modifier
    ) {
        // My Atlas text
        Text(
            text = "Sample App",
            style = AppTheme.typography.fenwickBold24,
            color = AppTheme.colors.midnight
        )
    }
}
