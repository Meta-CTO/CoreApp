package com.metacto.core.presentation.itemPicker.models

import com.metacto.core.utils.CommonParcelable

interface PickerItem : CommonParcelable {
    val key: String
    val title: String
}