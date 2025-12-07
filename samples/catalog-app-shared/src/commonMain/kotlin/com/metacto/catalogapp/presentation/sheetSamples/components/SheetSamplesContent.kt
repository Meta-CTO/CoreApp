package com.metacto.catalogapp.presentation.sheetSamples.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesContract.Event
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesContract.State
import com.metacto.catalogapp.presentation.sheetSamples.sheets.FormSheet
import com.metacto.catalogapp.presentation.sheetSamples.sheets.ListSheet
import com.metacto.catalogapp.presentation.sheetSamples.sheets.SimpleContentSheet
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun SheetSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Listen for sheet results
    LaunchedEffect(Unit) {
        navManager.onNavResult<FormSheet, String> { result ->
            onEvent(Event.OnSheetResult(result))
        }
    }

    LaunchedEffect(Unit) {
        navManager.onNavResult<ListSheet, String> { result ->
            onEvent(Event.OnSheetResult(result))
        }
    }

    // Container column
    AppScreenColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing16),
        title = "Sheet Samples",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        Text(
            text = "Bottom sheets are great for showing additional content or actions without leaving the current screen.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = spacings.spacing8)
        )

        PrimaryFilledButton(
            text = "Show Simple Content Sheet",
            onClick = {
                navManager.navigateToBottomSheet(SimpleContentSheet())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Show Form Sheet",
            onClick = {
                navManager.navigateToBottomSheet(FormSheet())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Show List Sheet",
            onClick = {
                navManager.navigateToBottomSheet(ListSheet())
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Show result if available
        if (state.resultText.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacings.spacing16),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Sheet Result:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(
                        start = spacings.spacing16,
                        end = spacings.spacing16,
                        top = spacings.spacing16,
                        bottom = spacings.spacing8
                    )
                )
                Text(
                    text = state.resultText,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = spacings.spacing16,
                        end = spacings.spacing16,
                        bottom = spacings.spacing16
                    )
                )
            }
        }
    }
}
