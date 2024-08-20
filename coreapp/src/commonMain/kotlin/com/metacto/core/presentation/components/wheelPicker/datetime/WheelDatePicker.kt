package com.metacto.core.presentation.components.wheelPicker.datetime

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.components.wheelPicker.SelectorProperties
import com.metacto.core.presentation.components.wheelPicker.WheelPickerDefaults
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.datetime.LocalDate

@Composable
fun WheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.EPOCH,
    maxDate: LocalDate = LocalDate.CYBER_ERA,
    yearsRange: IntRange? = IntRange(minDate.year, maxDate.year),
    size: DpSize = DpSize(
        width = CoreTheme.spacings.wheelDatePicker.pickerWidth,
        height = CoreTheme.spacings.wheelDatePicker.pickerHeight
    ),
    rowCount: Int = 3, // Number of rows to show
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate: (snappedDate: LocalDate) -> Unit = {}
) {
    DefaultWheelDatePicker(
        modifier = modifier,
        startDate = startDate,
        minDate = minDate,
        maxDate = maxDate,
        yearsRange = yearsRange,
        size = size,
        rowCount = rowCount,
        textStyle = textStyle,
        textColor = textColor,
        selectorProperties = selectorProperties,
        onSnappedDate = { snappedDate ->
            onSnappedDate(snappedDate.snappedLocalDate)
            snappedDate.snappedIndex
        }
    )
}