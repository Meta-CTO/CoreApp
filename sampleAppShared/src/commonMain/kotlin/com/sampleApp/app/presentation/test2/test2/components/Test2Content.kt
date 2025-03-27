package com.sampleApp.app.presentation.test2.test2.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.sampleApp.app.presentation.theme.AppTheme.colors
import com.sampleApp.app.presentation.theme.AppTheme.typography
import com.sampleApp.app.presentation.theme.AppTheme.shapes
import com.sampleApp.app.presentation.theme.AppTheme.spacings
import com.sampleApp.app.presentation.test2.test2.Test2Contract.State
import com.sampleApp.app.presentation.test2.test2.Test2Contract.Event

@Composable
internal fun Test2Content(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Test2 Screen"
        )
    }
}
