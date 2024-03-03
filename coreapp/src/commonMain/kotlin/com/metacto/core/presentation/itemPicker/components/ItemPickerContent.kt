package com.metacto.core.presentation.itemPicker.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.wheelPicker.WheelTextPicker
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.ItemPickerContract.State
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun ItemPickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {

    var currentSelectedIndex = remember { state.initialItemIndex }

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
                    selectedIndex = currentSelectedIndex
                )
            )
        }
    ) {
//        // Render wheel picker
//        WheelPicker(
//            modifier = Modifier.fillMaxWidth(),
//            count = state.items.size,
//            rowCount = 1,
//            unfocusedCount = state.unfocusedItemsCount,
//            isVertical = true,
//            itemSize = CoreTheme.spacings.pickerItemSize
//        ) { index ->
//            // Render item title
//            Text(
//                text = state.items[index].title,
//                textAlign = TextAlign.Center,
//                style = CoreTheme.typography.pickerItem,
//                color = CoreTheme.colors.pickerItem,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }

        WheelTextPicker(
            modifier = Modifier.fillMaxWidth(),
            startIndex = state.initialItemIndex,
            size = DpSize(CoreTheme.spacings.pickerItemSize, CoreTheme.spacings.pickerItemSize),
            texts = state.items.map { it.title },
            rowCount = 1,
            style = CoreTheme.typography.pickerItem,
            color = CoreTheme.colors.pickerItem,
            onScrollFinished = { index ->
                currentSelectedIndex = index
                null
            }
        )
    }
}