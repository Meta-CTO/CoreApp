package com.metacto.core.presentation.components.dateTime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.wheelPicker.datetime.CYBER_ERA
import com.metacto.core.presentation.components.wheelPicker.datetime.EPOCH
import com.metacto.core.presentation.components.wheelPicker.datetime.WheelDateTimePicker
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun AppDatePicker(
    modifier: Modifier = Modifier,
    selectedDate: LocalDateTime? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    minDate: LocalDateTime = LocalDateTime.EPOCH,
    maxDate: LocalDateTime = LocalDateTime.CYBER_ERA,
    onDateChange: (LocalDate) -> Unit
) {
   WheelDateTimePicker(
       modifier = modifier,
       startDateTime = selectedDate!!,
       minDateTime = minDate,
       maxDateTime = maxDate,
       onSnappedDateTime = { snappedDateTime ->
           onDateChange(snappedDateTime.date)
       }
   )
}