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
import com.metacto.coreApp.MR
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.dateTime.AppDatePicker
import com.metacto.core.presentation.components.dialogs.AppDialog
import com.metacto.core.presentation.theme.CoreTheme
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    selectedDate: LocalDate? = null,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
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
            AppDatePicker(
                selectedDate = currentDate,
                minDate = minDate,
                maxDate = maxDate,
                onDateChange = {
                    currentDate = it
                },
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