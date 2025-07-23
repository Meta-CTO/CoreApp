package com.metacto.core.ui.navigation

import com.metacto.kmm.core.CommonParcelable
import com.metacto.kmm.core.CommonParcelize
import com.metacto.kmm.core.CommonSerializable

@CommonParcelize
enum class NavigateBehaviour : CommonParcelable, CommonSerializable {
    Normal,
    ReplaceIfCurrent,
    KeepIfCurrent
}