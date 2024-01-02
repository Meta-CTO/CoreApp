package com.metacto.core.presentation.imagePicker.models

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
sealed class ImagePickerResult : CommonParcelable {
    @CommonParcelize
    data class ImagePicked(val bytes: ByteArray): ImagePickerResult()
    @CommonParcelize
    data object Cancelled: ImagePickerResult()
    @CommonParcelize
    data object ImageDeleted: ImagePickerResult()
}