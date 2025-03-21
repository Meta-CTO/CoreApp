package com.metacto.core.presentation.models

import com.metacto.core.utils.CommonSerializable
import org.jetbrains.compose.resources.DrawableResource

data class ImageUIModel(
    val id: String? = null,
    val bytes: ByteArray? = null,
    val resource: DrawableResource? = null,
    val url: String? = null,
    val isUpdating: Boolean = false
) : CommonSerializable {
    fun getData() = bytes ?: url ?: resource

    fun hasData(): Boolean {
        return bytes != null || url != null || resource != null
    }
}

fun ImageUIModel?.copyOrCreate(bytes: ByteArray? = null): ImageUIModel {
    return this?.copy(bytes = bytes) ?: ImageUIModel(bytes = bytes)
}