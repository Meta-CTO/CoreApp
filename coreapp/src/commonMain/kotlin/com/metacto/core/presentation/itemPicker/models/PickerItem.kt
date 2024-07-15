package com.metacto.core.presentation.itemPicker.models

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonSerializable

interface PickerItem : CommonParcelable, CommonSerializable {
    val key: String
    val title: String
}