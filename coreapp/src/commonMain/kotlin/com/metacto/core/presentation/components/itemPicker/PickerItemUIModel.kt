package com.metacto.core.presentation.components.itemPicker

import com.metacto.core.utils.CommonParcelize

@CommonParcelize
data class PickerItemUIModel(
    override val key: String,
    override val title: String
) : PickerItem
