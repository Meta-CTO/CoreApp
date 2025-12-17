package com.metacto.core.ui.components.pickers

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.datetime.LocalDate
import platform.UIKit.UIColor
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.systemBackgroundColor

@OptIn(ExperimentalComposeUiApi::class)
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
    val backgroundUiColor = remember(backgroundColor) {
        if (backgroundColor == Color.Transparent) {
            UIColor.systemBackgroundColor
        } else {
            UIColor(
                red = backgroundColor.red.toDouble(),
                green = backgroundColor.green.toDouble(),
                blue = backgroundColor.blue.toDouble(),
                alpha = backgroundColor.alpha.toDouble()
            )
        }
    }

    val view = remember(startDate, minDate, maxDate, backgroundUiColor) {
        NativeDatePicker(
            initialDate = startDate,
            style = DatePickerStyle(
                backgroundColor = backgroundUiColor,
                textColor = UIColor.blackColor,
                pickerStyle = DatePickerUIStyle.WHEELS,
                pickerMode = DatePickerDisplayMode.DATE_ONLY,
                interfaceStyle = UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified,
                minDate = minDate,
                maxDate = maxDate
            ),
            onDateChanged = onSnappedDate
        )
    }

    UIKitView(
        factory = { view },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        ),
        modifier = modifier.size(size)
    )
}
