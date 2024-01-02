package com.metacto.core.presentation.models

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
data class ImageUIModel(
    val id: Int? = null,
    val bytes: ByteArray? = null,
    val url: String? = null,
    val isUpdating: Boolean = false
) : CommonParcelable {
    fun getData() = bytes ?: url

    fun hasData(): Boolean {
        return bytes != null || url != null
    }
}

fun ImageUIModel?.copyOrCreate(bytes: ByteArray? = null): ImageUIModel {
    return this?.copy(bytes = bytes) ?: ImageUIModel(bytes = bytes)
}