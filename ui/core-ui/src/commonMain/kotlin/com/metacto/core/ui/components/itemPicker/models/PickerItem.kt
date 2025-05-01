package com.metacto.core.ui.components.itemPicker.models

import com.metacto.core.CommonParcelable
import com.metacto.core.CommonSerializable

interface PickerItem : CommonParcelable, CommonSerializable {
    val key: String
    val title: String
}