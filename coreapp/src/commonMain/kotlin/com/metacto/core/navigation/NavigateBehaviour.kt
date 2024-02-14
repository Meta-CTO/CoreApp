package com.metacto.core.navigation

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonSerializable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
enum class NavigateBehaviour : CommonParcelable, CommonSerializable {
    Normal,
    ReplaceIfCurrent,
    KeepIfCurrent
}