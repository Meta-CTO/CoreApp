package com.metacto.catalogapp.presentation.datePicker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.datePicker.DatePickerContract.Event
import com.metacto.catalogapp.presentation.datePicker.DatePickerContract.State
import com.metacto.catalogapp.presentation.theme.colors
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.pickers.NativeDatePicker
import com.metacto.core.ui.components.wheelPicker.datetime.WheelDatePicker
import com.metacto.core.ui.navigation.NavManager
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.koin.compose.koinInject

@Composable
internal fun DatePickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Date range setup
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val minDate = LocalDate(today.year - 10, 1, 1)
    val maxDate = LocalDate(today.year + 10, 12, 31)
    val startDate = state.selectedDate ?: today

    // Container column
    AppScreenColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing8),
        title = "Date Pickers",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        // Toggle between Native and Wheel pickers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacings.spacing8),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (state.useNativePicker) "Using Native Picker" else "Using Wheel Picker"
            )
            Switch(
                checked = state.useNativePicker,
                onCheckedChange = {
                    onEvent(Event.TogglePickerType)
                }
            )
        }

        Spacer(modifier = Modifier.height(spacings.spacing16))

        // Display selected date
        state.selectedDate?.let { date ->
            Text(
                text = "Selected Date: ${date.year}-${date.monthNumber.toString().padStart(2, '0')}-${date.dayOfMonth.toString().padStart(2, '0')}",
                modifier = Modifier.padding(vertical = spacings.spacing8)
            )

            Spacer(modifier = Modifier.height(spacings.spacing8))
        }

        // Date Picker
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.useNativePicker) {
                NativeDatePicker(
                    onSnappedDate = { date ->
                        onEvent(Event.OnDateSelected(date))
                    },
                    minDate = minDate,
                    maxDate = maxDate,
                    startDate = startDate,
                    size = DpSize(width = 300.dp, height = 200.dp),
                    backgroundColor = colors.background,
                    modifier = Modifier.padding(spacings.spacing16)
                )
            } else {
                WheelDatePicker(
                    onSnappedDate = { date ->
                        onEvent(Event.OnDateSelected(date))
                    },
                    minDate = minDate,
                    maxDate = maxDate,
                    startDate = startDate,
                    size = DpSize(width = 300.dp, height = 200.dp),
                    modifier = Modifier.padding(spacings.spacing16)
                )
            }
        }

        Spacer(modifier = Modifier.height(spacings.spacing16))

        // Info text
        Text(
            text = if (state.useNativePicker) {
                "Native Date Picker uses platform-specific date picker UI"
            } else {
                "Wheel Date Picker provides a cross-platform custom wheel interface"
            },
            modifier = Modifier.padding(spacings.spacing8)
        )
    }
}
