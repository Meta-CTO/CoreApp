package com.sampleApp.app.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.sampleApp.app.presentation.theme.AppTheme.colors
import com.sampleApp.app.presentation.theme.AppTheme.typography
import com.sampleApp.app.presentation.theme.AppTheme.shapes
import com.sampleApp.app.presentation.theme.AppTheme.spacings
import com.sampleApp.app.presentation.profile.ProfileContract.State
import com.sampleApp.app.presentation.profile.ProfileContract.Event

@Composable
internal fun ProfileContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Profile Screen"
        )
    }
}
