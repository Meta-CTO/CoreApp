package com.metacto.core.ui.components.itemPicker.models

import com.metacto.kmm.core.CommonParcelable
import com.metacto.kmm.core.CommonSerializable

interface PickerItem : CommonParcelable, CommonSerializable {
    val key: String
    val title: String
}