package com.metacto.core.presentation.components.dialogs.datePicker

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
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onDatePicked: (LocalDate) -> Unit,
    onDismiss: () -> Unit = {}
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
        showToolbar = false,
        padding = PaddingValues(
            vertical = CoreTheme.spacings.paddingXLarge,
            horizontal = CoreTheme.spacings.noSpacing
        )
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
                size = DpSize(getScreenSize().first.toDp() - CoreTheme.spacings.pickerPadding, CoreTheme.spacings.datePickerWheelHeight),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CoreTheme.spacings.datePickerHeight)
            )

            // Ok button
            PrimaryFilledButton(
                text = stringResource(MR.strings.ok),
                onClick = ::handleOkClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = CoreTheme.spacings.paddingXXXLarge)
                    .padding(horizontal = CoreTheme.spacings.paddingXLarge)
            )
        }
    }
}