package com.metacto.core.presentation.components.dateTime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.wheelPicker.datetime.WheelTimePicker
import com.metacto.core.presentation.components.wheelPicker.datetime.now
import kotlinx.datetime.LocalTime

@Composable
fun AppTimePicker(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = LocalTime.now(),
    onTimeChanged: (hour: Int, minute: Int, isAm: Boolean) -> Unit
) {
    WheelTimePicker(
        modifier = modifier,
        startTime = selectedTime,
        onSnappedTime = { snappedTime ->
            onTimeChanged(snappedTime.hour, snappedTime.minute, snappedTime.hour < 12)
        }
    )
}