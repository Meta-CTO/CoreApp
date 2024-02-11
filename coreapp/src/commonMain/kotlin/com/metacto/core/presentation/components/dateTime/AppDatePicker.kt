package com.metacto.core.presentation.components.dateTime

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.dateTime.DatePickerDefaults.DEFAULT_MAX_DATE
import com.metacto.core.presentation.components.dateTime.DatePickerDefaults.DEFAULT_MIN_DATE
import com.metacto.core.presentation.components.wheelPicker.DefaultWheel
import com.metacto.core.utils.extensions.capitalizeFirstLetter
import com.metacto.core.utils.extensions.daysInMonth
import com.metacto.core.utils.extensions.getCurrentDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlin.math.min

@Composable
fun AppDatePicker(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate? = getCurrentDate(),
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onDateChange: (LocalDate) -> Unit
) {
    // Prepare min & max dates
    val minDate = minDate ?: DEFAULT_MIN_DATE
    val maxDate = maxDate ?: DEFAULT_MAX_DATE

    // Prepare available dates
    val years = (minDate.year..maxDate.year).toList()
    val months = Month.values().map { it.name.capitalizeFirstLetter() }
    var days by remember { mutableStateOf((1..(selectedDate ?: maxDate).dayOfMonth).toList()) }

    // Selected date indices
    var selectedYearIndex by remember(selectedDate) {
        val index = selectedDate?.let { years.indexOf(it.year) } ?: 0
        mutableStateOf(index)
    }

    var selectedMonthIndex by remember(selectedDate) {
        val index = selectedDate?.let { it.monthNumber - 1 } ?: 0
        mutableStateOf(index)
    }

    var selectedDayIndex by remember(selectedDate) {
        val index = selectedDate?.let { it.dayOfMonth - 1 } ?: 0
        mutableStateOf(index)
    }

    // Function to update days based on selected year and month
    fun updateDays() {
        val selectedYear = years[selectedYearIndex]

        // Check for maxDate to update days list accordingly
        val maxDays =
            if (selectedYear == maxDate.year && selectedMonthIndex == maxDate.monthNumber - 1) {
                maxDate.dayOfMonth
            } else {
                daysInMonth(selectedYear, selectedMonthIndex)
            }

        // Update days list
        days = (1..maxDays).toList()
        selectedDayIndex = min(selectedDayIndex, maxDays - 1)
    }

    // Date picker
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Month picker
        DefaultWheel(
            modifier = Modifier.weight(1f),
            items = months,
            initialIndex = selectedMonthIndex,
            onItemChange = {
                selectedMonthIndex = it
                updateDays()
            }
        )

        // Day picker
        DefaultWheel(
            modifier = Modifier.weight(1f),
            items = days,
            initialIndex = selectedDayIndex,
            onItemChange = {
                selectedDayIndex = it
            }
        )

        // Year picker
        DefaultWheel(
            modifier = Modifier.weight(1f),
            items = years,
            initialIndex = selectedYearIndex,
            onItemChange = {
                selectedYearIndex = it
                updateDays()
            }
        )
    }


    // Initial update of days
    updateDays()

    // Call onDateChange when any of the wheels change
    DisposableEffect(selectedYearIndex, selectedMonthIndex, selectedDayIndex) {
        onDateChange.invoke(
            LocalDate(
                years[selectedYearIndex],
                selectedMonthIndex + 1,
                days[selectedDayIndex]
            )
        )
        onDispose { }
    }
}