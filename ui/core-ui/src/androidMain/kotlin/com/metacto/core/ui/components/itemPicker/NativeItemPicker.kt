package com.metacto.core.ui.components.itemPicker

import com.metacto.core.ui.components.itemPicker.models.PickerItem

internal actual class NativeItemPicker {
    actual fun display(
        items: List<PickerItem>,
        selectedItem: PickerItem?,
        onItemSelected: (PickerItem) -> Unit
    ) {
        error("Native item picker is not supported on Android. Please user ItemPickerSheet.")
    }
}