package com.metacto.core.ui.components.imagePicker.models

import com.metacto.core.CommonParcelable
import com.metacto.core.CommonParcelize

@CommonParcelize
sealed class ImagePickerResult : CommonParcelable {
    @CommonParcelize
    data class ImagePicked(val bytes: ByteArray): ImagePickerResult()
    @CommonParcelize
    data object Cancelled: ImagePickerResult()
    @CommonParcelize
    data object ImageDeleted: ImagePickerResult()
}