package com.metacto.core.presentation.components.dateTime

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.wheelPicker.DefaultWheel
import com.metacto.core.utils.extensions.get12FormatHour
import com.metacto.core.utils.extensions.getCurrentTime
import com.metacto.core.utils.extensions.isAM
import kotlinx.datetime.LocalTime

@Composable
fun AppTimePicker(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = getCurrentTime(),
    onTimeChanged: (hour: Int, minute: Int, isAm: Boolean) -> Unit
) {
    // Prepare states
    var currentHourIndex by remember(selectedTime) {
        val index = HOURS_LIST.indexOf(selectedTime.get12FormatHour())
        mutableStateOf(index)
    }
    var currentMinuteIndex by remember(selectedTime) {
        val index = MINUTES_LIST.indexOf(selectedTime.minute)
        mutableStateOf(index)
    }
    var currentAmIndex by remember(selectedTime) {
        val index = if (selectedTime.isAM()) AM_INDEX else PM_INDEX
        mutableStateOf(index)
    }

    // Prepare trigger time changed function
    fun triggerTimeChanged() {
        onTimeChanged.invoke(
            HOURS_LIST[currentHourIndex],
            MINUTES_LIST[currentMinuteIndex],
            currentAmIndex == AM_INDEX
        )
    }

    // Container row
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hours picker
        DefaultWheel(
            modifier = Modifier.weight(1f),
            items = HOURS_LIST,
            initialIndex = currentHourIndex,
            onItemChange = {
                currentHourIndex = it
                triggerTimeChanged()
            }
        )

        // Minutes picker
        DefaultWheel(
            modifier = Modifier.weight(1f),
            items = MINUTES_LIST,
            initialIndex = currentMinuteIndex,
            onItemChange = {
                currentMinuteIndex = it
                triggerTimeChanged()
            }
        )

        // AM picker
        DefaultWheel(
            modifier = Modifier.weight(1f),
            items = AM_LIST,
            initialIndex = currentAmIndex,
            onItemChange = {
                currentAmIndex = it
                triggerTimeChanged()
            }
        )
    }
}

// Prepare times lists
private val HOURS_LIST = (1..12).toList()
private val MINUTES_LIST = (0..59).toList()
private val AM_LIST = listOf("AM", "PM")
private const val AM_INDEX = 0
private const val PM_INDEX = 1