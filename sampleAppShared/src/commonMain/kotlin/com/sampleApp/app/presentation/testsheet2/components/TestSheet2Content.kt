package com.sampleApp.app.presentation.testsheet2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.sampleApp.app.presentation.theme.AppTheme.colors
import com.sampleApp.app.presentation.theme.AppTheme.typography
import com.sampleApp.app.presentation.theme.AppTheme.shapes
import com.sampleApp.app.presentation.theme.AppTheme.spacings
import com.sampleApp.app.presentation.testsheet2.TestSheet2Contract.State
import com.sampleApp.app.presentation.testsheet2.TestSheet2Contract.Event

@Composable
internal fun TestSheet2Content(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Green)
    ) {
        Text(
            text = "TestSheet2 Screen"
        )
    }
}
