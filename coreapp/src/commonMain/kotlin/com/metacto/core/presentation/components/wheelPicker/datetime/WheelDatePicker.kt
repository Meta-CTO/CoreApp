package com.metacto.core.presentation.components.wheelPicker.datetime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.components.wheelPicker.DefaultSelectorProperties
import com.metacto.core.presentation.components.wheelPicker.SelectorProperties
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
    rowCount: Int = CoreTheme.spacings.wheelDatePicker.rowCount,
    textStyle: TextStyle = CoreTheme.typography.wheelDatePicker.textStyle,
    textColor: Color = CoreTheme.colors.wheelDatePicker.textColor,
    selectorColor: Color = CoreTheme.colors.wheelDatePicker.selectorColor,
    selectorStrokeColor: Color = CoreTheme.colors.wheelDatePicker.selectorStrokeColor,
    selectorBorderWidth: Dp = CoreTheme.spacings.wheelDatePicker.borderWidth,
    selectorShape: RoundedCornerShape = CoreTheme.shapes.wheelDatePicker.selectorShape,
    selectorProperties: SelectorProperties = DefaultSelectorProperties(
        enabled = true,
        shape = selectorShape,
        color = selectorColor,
        border = BorderStroke(
            width = selectorBorderWidth,
            color = selectorStrokeColor
        )
    ),
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