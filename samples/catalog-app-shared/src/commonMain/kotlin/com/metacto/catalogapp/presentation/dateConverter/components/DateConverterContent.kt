package com.metacto.catalogapp.presentation.dateConverter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.dateConverter.DateConverterContract.Event
import com.metacto.catalogapp.presentation.dateConverter.DateConverterContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.domain.CoreConstants
import com.metacto.core.date.formatToRelativeDate
import com.metacto.core.date.parseLocalDateTime
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import kotlinx.datetime.TimeZone
import org.koin.compose.koinInject

@Composable
internal fun DateConverterContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // State for storing conversion results
    var dateString by remember { mutableStateOf("2025-12-07T16:31:50.976Z") }
    var parsedDateTime by remember { mutableStateOf<String?>(null) }
    var relativeDate by remember { mutableStateOf<String?>(null) }

    // Container column
    AppScreenColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing8),
        title = "Date Converter",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        // Input date string display
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Input Date String:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Format: ${CoreConstants.SERVER_DATE_FORMAT}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Convert button
        PrimaryFilledButton(
            text = "Convert Date",
            onClick = {
                val localDateTime = dateString.parseLocalDateTime(
                    format = CoreConstants.SERVER_DATE_FORMAT
                )

                parsedDateTime = localDateTime?.toString()
                relativeDate = localDateTime?.formatToRelativeDate(TimeZone.UTC).orEmpty()
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Parsed LocalDateTime result
        parsedDateTime?.let { parsed ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Parsed LocalDateTime:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = parsed,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // Relative date result
        relativeDate?.let { relative ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Relative Date (UTC):",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = relative,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
