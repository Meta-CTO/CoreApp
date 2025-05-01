package com.metacto.core.ui.navigation

import com.metacto.core.CommonParcelable
import com.metacto.core.CommonParcelize
import com.metacto.core.CommonSerializable

@CommonParcelize
enum class NavigateBehaviour : CommonParcelable, CommonSerializable {
    Normal,
    ReplaceIfCurrent,
    KeepIfCurrent
}