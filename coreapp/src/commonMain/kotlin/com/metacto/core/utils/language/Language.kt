package com.metacto.core.utils.language

import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.CommonParcelize
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class Language(
    val code: String = "",
    val name: String = "",
    val isRtl: Boolean = false
) : PickerItem {
    override val key = code
    override val title = name
}

val Language.Companion.English: Language
    get() = Language(
        code = "en",
        name = "English",
        isRtl = false
    )