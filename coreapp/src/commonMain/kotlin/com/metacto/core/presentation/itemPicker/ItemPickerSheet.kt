package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem

expect class ItemPickerSheet constructor(
    items: List<PickerItem>,
    selectedItem: PickerItem? = null,
    unfocusedItemsCount: Int = ItemPickerContract.DEFAULT_UNFOCUSED_ITEMS_COUNT
) : BaseSheet<ItemPickerViewModel> {

    val items: List<PickerItem>
    val selectedItem: PickerItem?
    val unfocusedItemsCount: Int
}