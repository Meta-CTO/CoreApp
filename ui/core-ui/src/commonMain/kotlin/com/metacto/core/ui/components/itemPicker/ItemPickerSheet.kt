package com.metacto.core.ui.components.itemPicker

import com.metacto.core.PlatformType
import com.metacto.core.ui.base.CoreSheet
import com.metacto.core.ui.components.itemPicker.models.PickerItem

expect class ItemPickerSheet(
    items: List<PickerItem>,
    selectedItem: PickerItem? = null,
    maxItemLines: Int = 1,
    visibleItemCount: Int = 5,
    canSearch: Boolean = false,
    platform: PlatformType? = null
) : CoreSheet<ItemPickerViewModel> {

    val items: List<PickerItem>
    val selectedItem: PickerItem?
    val maxItemLines: Int
    val visibleItemCount: Int
    val canSearch: Boolean
    val platform: PlatformType?
}