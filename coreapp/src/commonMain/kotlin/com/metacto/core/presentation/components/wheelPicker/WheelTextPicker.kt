package com.metacto.core.presentation.components.wheelPicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    size: DpSize = DpSize(CoreTheme.spacings.defaultWheelPickerWidth, CoreTheme.spacings.defaultWheelPickerHeight),
    texts: List<String>,
    rowCount: Int,
    style: TextStyle = CoreTheme.typography.pickerItem,
    color: Color = CoreTheme.colors.pickerItem,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    WheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        size = size,
        count = texts.size,
        rowCount = rowCount,
        selectorProperties = selectorProperties,
        onScrollFinished = onScrollFinished
    ) { index ->
        SingleLineText(
            text = texts[index],
            textAlign = TextAlign.Center,
            style = style,
            color = color
        )
    }
}