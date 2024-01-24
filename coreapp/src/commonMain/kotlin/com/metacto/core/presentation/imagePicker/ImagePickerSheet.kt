package com.metacto.core.presentation.imagePicker

import com.metacto.core.presentation.base.BaseSheet

expect class ImagePickerSheet constructor(
    allowGallery: Boolean = true,
    allowCamera: Boolean = true,
    showDeleteAction: Boolean = false,
    enableCropping: Boolean = false,
    aspectRatioX: Int? = null,
    aspectRatioY: Int? = null
) : BaseSheet<ImagePickerViewModel> {

    val allowGallery: Boolean
    val allowCamera: Boolean
    val showDeleteAction: Boolean
    val enableCropping: Boolean
    val aspectRatioX: Int?
    val aspectRatioY: Int?
}