package com.metacto.catalogapp.presentation.customDialog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.Event
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun CustomDialogSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Container column
    AppScreenColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing16),
        title = "Custom Dialog Samples",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        Text(
            text = "Custom dialogs allow you to show any composable content inside a dialog with optional toolbar and buttons.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = spacings.spacing8)
        )

        PrimaryFilledButton(
            text = "Simple Dialog",
            onClick = {
                onEvent(Event.ShowSimpleDialog)
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Dialog with Toolbar",
            onClick = {
                onEvent(Event.ShowDialogWithToolbar)
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Dialog with Button",
            onClick = {
                onEvent(Event.ShowDialogWithButton)
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Dialog with Form",
            onClick = {
                onEvent(Event.ShowDialogWithForm)
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Complex Dialog",
            onClick = {
                onEvent(Event.ShowComplexDialog)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
