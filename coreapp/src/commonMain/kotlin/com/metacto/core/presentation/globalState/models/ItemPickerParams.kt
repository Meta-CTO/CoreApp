package com.metacto.core.presentation.globalState.models

import com.metacto.core.presentation.itemPicker.models.PickerItem

data class ItemPickerParams(
    val items: List<PickerItem>,
    val selectedItem: PickerItem? = null,
    val onItemSelected: (PickerItem) -> Unit
)