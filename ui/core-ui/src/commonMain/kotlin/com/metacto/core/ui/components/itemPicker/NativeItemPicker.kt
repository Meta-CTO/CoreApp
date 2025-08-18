package com.metacto.core.ui.components.itemPicker

import com.metacto.core.ui.components.itemPicker.models.PickerItem

internal expect class NativeItemPicker {
    fun display(
        items: List<PickerItem>,
        selectedItem: PickerItem? = null,
        onItemSelected: (PickerItem) -> Unit
    )
}