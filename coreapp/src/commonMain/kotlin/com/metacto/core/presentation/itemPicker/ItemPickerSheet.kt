package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.PlatformType

expect class ItemPickerSheet(
    items: List<PickerItem>,
    selectedItem: PickerItem? = null,
    maxLines: Int = 1,
    displayableItemsCount: Int = 5,
    canSearch: Boolean = false,
    platform: PlatformType? = null
) : BaseSheet<ItemPickerViewModel> {

    val items: List<PickerItem>
    val selectedItem: PickerItem?
    val maxLines: Int
    val displayableItemsCount: Int
    val canSearch: Boolean
    val platform: PlatformType?
}