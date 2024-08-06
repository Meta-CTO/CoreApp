package com.metacto.core.navigation

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize
import com.metacto.core.utils.CommonSerializable

@CommonParcelize
enum class NavigateBehaviour : CommonParcelable, CommonSerializable {
    Normal,
    ReplaceIfCurrent,
    KeepIfCurrent
}