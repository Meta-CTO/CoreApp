package com.metacto.core.presentation.itemPicker.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.wheelPicker.WheelPicker
import com.metacto.core.presentation.components.wheelPicker.rememberFWheelPickerState
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.ItemPickerContract.State
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun ItemPickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Init wheel state
    val wheelState = rememberFWheelPickerState(state.initialItemIndex)

    // Scroll to initial index
    LaunchedEffect(state.initialItemIndex) {
        wheelState.scrollToIndex(state.initialItemIndex)
    }

    // Bottom sheet container
    BottomSheetContainer(
        startIcon = Icons.Default.Close,
        endIcon = Icons.Default.Check,
        onStartIconClick = {
            onEvent(Event.CloseClicked)
        },
        onEndIconClick = {
            onEvent(
                Event.DoneClicked(
                    selectedIndex = wheelState.currentIndex
                )
            )
        }
    ) {
        // Render wheel picker
        WheelPicker(
            state = wheelState,
            count = state.items.size,
            unfocusedCount = 3,
            isVertical = true,
            itemSize = CoreTheme.spacings.pickerItemSize,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render item title
            Text(
                text = state.items[it].title,
                textAlign = TextAlign.Center,
                style = CoreTheme.typography.pickerItem,
                color = CoreTheme.colors.pickerItem,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}