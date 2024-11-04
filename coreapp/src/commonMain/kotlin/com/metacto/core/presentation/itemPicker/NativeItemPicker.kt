package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.itemPicker.models.PickerItem

internal expect class NativeItemPicker {
    fun display(
        items: List<PickerItem>,
        selectedItem: PickerItem? = null,
        onItemSelected: (PickerItem) -> Unit
    )
}