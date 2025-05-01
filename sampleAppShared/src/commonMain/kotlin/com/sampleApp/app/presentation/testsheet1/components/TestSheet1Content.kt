package com.sampleApp.app.presentation.testsheet1.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.sampleApp.app.presentation.testsheet1.TestSheet1Contract.Event
import com.sampleApp.app.presentation.testsheet1.TestSheet1Contract.State
import com.sampleApp.app.presentation.testsheet2.TestSheet2
import org.koin.compose.koinInject

@Composable
internal fun TestSheet1Content(
    state: State,
    onEvent: (Event) -> Unit
) {
    val navManager = koinInject<NavManager>()

    // Container column
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.Gray)
    ) {
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Open sheet 2",
            onClick = {
                navManager.navigateToBottomSheet(TestSheet2())
            }
        )
    }
}
