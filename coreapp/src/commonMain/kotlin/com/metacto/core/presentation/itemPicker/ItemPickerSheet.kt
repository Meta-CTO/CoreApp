package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.PlatformType

expect class ItemPickerSheet(
    items: List<PickerItem>,
    selectedItem: PickerItem? = null,
    maxItemLines: Int = 1,
    visibleItemCount: Int = 5,
    canSearch: Boolean = false,
    platform: PlatformType? = null
) : BaseSheet<ItemPickerViewModel> {

    val items: List<PickerItem>
    val selectedItem: PickerItem?
    val maxItemLines: Int
    val visibleItemCount: Int
    val canSearch: Boolean
    val platform: PlatformType?
}