package com.metacto.core.presentation.components.dialogs.datePicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.dialogs.AppDialog
import com.metacto.core.presentation.components.wheelPicker.SelectorProperties
import com.metacto.core.presentation.components.wheelPicker.WheelPickerDefaults
import com.metacto.core.presentation.components.wheelPicker.datetime.CYBER_ERA
import com.metacto.core.presentation.components.wheelPicker.datetime.EPOCH
import com.metacto.core.presentation.components.wheelPicker.datetime.WheelDatePicker
import com.metacto.core.presentation.components.wheelPicker.datetime.now
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.getScreenSize
import com.metacto.core.utils.extensions.toDp
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    selectedDate: LocalDate? = null,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    rowCount: Int = 5,
    showToolbar: Boolean = CoreTheme.spacings.datePickerDialog.showToolbar,
    selectorBg: Color = CoreTheme.colors.datePickerDialog.selectorBgColor,
    selectorBorderColor: Color = CoreTheme.colors.datePickerDialog.selectorBorderColor,
    onDatePicked: (LocalDate) -> Unit,
    onDismiss: () -> Unit = {},
    okButtonPaddingHorizontal: PaddingValues = PaddingValues(horizontal = CoreTheme.spacings.datePickerDialog.btnPaddingHorizontal),
    okButtonPaddingTop: PaddingValues = PaddingValues(horizontal = CoreTheme.spacings.datePickerDialog.btnPaddingTop),
    wheelHeight: Dp = CoreTheme.spacings.datePickerDialog.wheelHeight,
    pickerPadding: Dp = CoreTheme.spacings.datePickerDialog.padding,
    btnBgColor: Color = CoreTheme.colors.datePickerDialog.btnBgColor,
    btnTextColor: Color = CoreTheme.colors.datePickerDialog.btnTextColor,
    btnTextStyle: TextStyle = CoreTheme.typography.datePickerDialog.btnTextStyle,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(
        color = selectorBg, border = BorderStroke(
            width = CoreTheme.spacings.datePickerDialog.selectorBorderWidth,
            color = selectorBorderColor
        )
    ),
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.datePickerDialog.paddingVertical,
        horizontal = CoreTheme.spacings.datePickerDialog.paddingHorizontal
    )
) {
    var currentDate by remember {
        mutableStateOf(selectedDate)
    }

    // Prepare ok click handler
    fun handleOkClick() {
        // Check if no selected date
        if (currentDate == null) {
            onDismiss.invoke()
            return
        }

        // Everything is ok
        onDatePicked.invoke(currentDate!!)
    }

    // Render app dialog
    AppDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
        showToolbar = showToolbar,
        contentPadding = padding
    ) {
        // Container column
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render date picker
            WheelDatePicker(
                rowCount = rowCount,
                startDate = currentDate ?: LocalDate.now(),
                minDate = minDate ?: LocalDate.EPOCH,
                maxDate = maxDate ?: LocalDate.CYBER_ERA,
                selectorProperties = selectorProperties,
                onSnappedDate = { localDate ->
                    currentDate = localDate
                },
                size = DpSize(
                    getScreenSize().first.toDp() - pickerPadding,
                    wheelHeight
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(wheelHeight)
            )

            // Ok button
            PrimaryFilledButton(
                text = stringResource(MR.strings.ok),
                onClick = ::handleOkClick,
                backgroundColor = btnBgColor,
                textColor = btnTextColor,
                textStyle = btnTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(okButtonPaddingTop)
                    .padding(okButtonPaddingHorizontal)
            )
        }
    }
}