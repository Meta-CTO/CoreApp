package com.metacto.core.presentation.components.itemPicker

import com.metacto.core.utils.CommonParcelable

interface PickerItem : CommonParcelable {
    val key: String
    val title: String
}