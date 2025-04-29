package com.metacto.core.language

import com.metacto.core.CommonParcelable
import com.metacto.core.CommonParcelize

@CommonParcelize
data class Language(
    val code: String = "",
    val name: String = "",
    val isRtl: Boolean = false
) : CommonParcelable {
    companion object
}

val Language.Companion.English: Language
    get() = Language(
        code = "en",
        name = "English",
        isRtl = false
    )