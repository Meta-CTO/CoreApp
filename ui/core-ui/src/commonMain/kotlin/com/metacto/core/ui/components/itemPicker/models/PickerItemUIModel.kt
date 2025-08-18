package com.metacto.core.ui.components.itemPicker.models

import com.metacto.kmm.core.CommonParcelize

@CommonParcelize
data class PickerItemUIModel(
    override val key: String,
    override val title: String
) : PickerItem
