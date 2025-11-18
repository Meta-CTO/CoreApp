package com.metacto.core.ui.components.pickers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import com.metacto.core.ui.components.wheelPicker.datetime.WheelDatePicker
import kotlinx.datetime.LocalDate

@Composable
actual fun NativeDatePicker(
    onSnappedDate: (LocalDate) -> Unit,
    minDate: LocalDate,
    maxDate: LocalDate,
    startDate: LocalDate,
    size: DpSize,
    modifier: Modifier,
    backgroundColor: Color
) {
    WheelDatePicker(
        onSnappedDate = onSnappedDate,
        minDate = minDate,
        maxDate = maxDate,
        startDate = startDate,
        size = size,
        modifier = modifier
    )
}
