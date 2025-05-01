package com.metacto.core.ui.components.itemPicker

import com.metacto.core.PlatformType
import com.metacto.core.ui.base.BaseSheet
import com.metacto.core.ui.components.itemPicker.models.PickerItem

expect class ItemPickerSheet(
    items: List<PickerItem>,
    selectedItem: PickerItem? = null,
    canSearch: Boolean = false,
    platform: PlatformType? = null
) : BaseSheet<ItemPickerViewModel> {

    val items: List<PickerItem>
    val selectedItem: PickerItem?
    val canSearch: Boolean
    val platform: PlatformType?
}