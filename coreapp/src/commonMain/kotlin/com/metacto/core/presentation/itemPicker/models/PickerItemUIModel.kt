package com.metacto.core.presentation.itemPicker.models

import com.metacto.core.utils.CommonParcelize

@CommonParcelize
data class PickerItemUIModel(
    override val key: String,
    override val title: String
) : PickerItem
