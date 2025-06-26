package com.metacto.core.presentation.components.wheelPicker

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    size: DpSize = DpSize(
        width = CoreTheme.spacings.wheelTextPicker.pickerWidth,
        height = CoreTheme.spacings.wheelTextPicker.pickerHeight
    ),
    texts: List<String>,
    rowCount: Int,
    style: TextStyle = CoreTheme.typography.wheelTextPicker.textStyle,
    color: Color = CoreTheme.colors.wheelTextPicker.textColor,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    onItemClicked: (() -> Unit)? = null,
    maxLines: Int = 1
) {
    WheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        size = size,
        count = texts.size,
        rowCount = rowCount,
        selectorProperties = selectorProperties,
        onScrollFinished = onScrollFinished,
    ) { index, clickable ->
        Text(
            text = texts[index],
            textAlign = TextAlign.Center,
            maxLines = maxLines,
            style = style,
            color = color,
            modifier = Modifier.noRippleClickable(
                enabled = clickable,
                onClick = { onItemClicked?.invoke() }
            )
        )
    }
}