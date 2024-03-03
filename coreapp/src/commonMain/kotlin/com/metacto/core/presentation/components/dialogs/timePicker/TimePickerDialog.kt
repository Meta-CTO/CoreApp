package com.metacto.core.presentation.components.dialogs.timePicker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.dateTime.AppTimePicker
import com.metacto.core.presentation.components.dialogs.AppDialog
import com.metacto.core.presentation.components.wheelPicker.datetime.MAX
import com.metacto.core.presentation.components.wheelPicker.datetime.MIN
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.getCurrentTime
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalTime
import org.koin.compose.rememberKoinInject

@Suppress("NAME_SHADOWING")
@Composable
internal fun TimePickerDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    selectedTime: LocalTime? = null,
    minTime: LocalTime? = null,
    maxTime: LocalTime? = null,
    onTimePicked: (LocalTime) -> Unit,
    onDismiss: () -> Unit = {}
) {
    // Get main objects
    val globalState = rememberKoinInject<ICoreGlobalState>()
    val resourceProvider = rememberKoinInject<IResourceProvider>()

    // Config times
    val selectedTime = selectedTime ?: getCurrentTime()
    val minTime = minTime ?: LocalTime.MIN
    val maxTime = maxTime ?: LocalTime.MAX

    // Prepare selected time states
    var currentHour by remember {
        mutableStateOf(0)
    }
    var currentMinute by remember {
        mutableStateOf(0)
    }
    var isCurrentAm by remember {
        mutableStateOf(false)
    }

    // Prepare ok click handler
    fun handleOkClick() {
        // Prepare valid hour
        val validTimeHour = currentHour.let {
            val hour = if (it == 12) 0 else it
            return@let if (isCurrentAm) hour else hour + 12
        }

        // Create local time object
        val selectedTime = LocalTime(
            hour = validTimeHour,
            minute = currentMinute
        )

        // Validate min time
        if (selectedTime < minTime) {
            // Show error
            globalState.snackBar(
                SnackBarParams(
                    type = SnackBarType.ERROR,
                    message = resourceProvider.getString(
                        MR.strings.minimum_allowed_time_is_s,
                        minTime.toString()
                    )
                )
            )
            return
        }

        // Validate max time
        if (selectedTime > maxTime) {
            // Show error
            globalState.snackBar(
                SnackBarParams(
                    type = SnackBarType.ERROR,
                    message = resourceProvider.getString(
                        MR.strings.maximum_allowed_time_is_s,
                        maxTime.toString()
                    )
                )
            )
            return
        }

        // Everything is ok
        onTimePicked.invoke(selectedTime)
    }

    // Render app dialog
    AppDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
        showToolbar = false
    ) {
        // Container column
        Column(
            modifier = Modifier.padding(CoreTheme.spacings.paddingXLarge)
        ) {
            // Render time picker
            AppTimePicker(
                selectedTime = selectedTime,
                onTimeChanged = { hour, minute, isAm ->
                    currentHour = hour
                    currentMinute = minute
                    isCurrentAm = isAm
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = CoreTheme.spacings.paddingXXLarge
                    )
            )

            // Ok button
            PrimaryFilledButton(
                text = stringResource(MR.strings.ok),
                onClick = ::handleOkClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = CoreTheme.spacings.paddingXXXLarge
                    )
            )
        }
    }
}