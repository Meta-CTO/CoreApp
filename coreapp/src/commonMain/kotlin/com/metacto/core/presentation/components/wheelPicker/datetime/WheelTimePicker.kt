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
import kotlinx.datetime.LocalTime

@Composable
fun WheelTimePicker(
  modifier: Modifier = Modifier,
  startTime: LocalTime = LocalTime.now(),
  minTime: LocalTime = LocalTime.MIN,
  maxTime: LocalTime = LocalTime.MAX,
  timeFormat: TimeFormat = TimeFormat.HOUR_24,
  size: DpSize = DpSize(CoreTheme.spacings.wheelTimePickerWidth, CoreTheme.spacings.wheelTimePickerHeight),
  rowCount: Int = 3, // Number of rows to show
  textStyle: TextStyle = MaterialTheme.typography.titleMedium,
  textColor: Color = LocalContentColor.current,
  selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
  onSnappedTime: (snappedTime: LocalTime) -> Unit = {},
) {
  DefaultWheelTimePicker(
    modifier,
    startTime,
    minTime,
    maxTime,
    timeFormat,
    size,
    rowCount,
    textStyle,
    textColor,
    selectorProperties,
    onSnappedTime = { snappedTime, _ ->
      onSnappedTime(snappedTime.snappedLocalTime)
      null
    }
  )
}