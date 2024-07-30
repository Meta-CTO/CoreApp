package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem

expect class ItemPickerSheet(
    items: List<PickerItem>,
    selectedItem: PickerItem? = null,
    canSearch: Boolean = false
) : BaseSheet<ItemPickerViewModel> {

    val items: List<PickerItem>
    val selectedItem: PickerItem?
    val canSearch: Boolean
}