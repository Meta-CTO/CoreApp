package com.metacto.core.presentation.components.itemPicker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.wheelPicker.WheelPicker
import com.metacto.core.presentation.components.wheelPicker.rememberFWheelPickerState
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.orZero

@Composable
fun ItemPickerContent(
    items: List<PickerItem>,
    selectedItem: PickerItem?,
    onItemSelected: (PickerItem) -> Unit,
    onCloseClick: () -> Unit
) {
    // Prepare the initial index
    val initialIndex = remember {
        if (selectedItem != null) {
            items.indexOfFirst { it.key == selectedItem.key }.orZero()
        } else {
            0
        }
    }

    // Init wheel state
    val wheelState = rememberFWheelPickerState(initialIndex)

    // Bottom sheet container
    BottomSheetContainer(
        startIcon = Icons.Default.Close,
        endIcon = Icons.Default.Check,
        onStartIconClick = onCloseClick,
        onEndIconClick = {
            onItemSelected.invoke(
                items[wheelState.currentIndex]
            )
        }
    ) {
        // Render wheel picker
        WheelPicker(
            state = wheelState,
            count = items.size,
            unfocusedCount = 3,
            isVertical = true,
            itemSize = CoreTheme.spacings.pickerItemSize,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render item title
            Text(
                text = items[it].title,
                textAlign = TextAlign.Center,
                style = CoreTheme.typography.bodyXLarge,
                color = CoreTheme.colors.secondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}