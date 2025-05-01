package com.metacto.core.ui.components.imagePicker

import androidx.compose.runtime.Composable
import com.metacto.core.ui.base.BaseSheet

expect class ImagePickerSheet(
    allowGallery: Boolean = true,
    allowCamera: Boolean = true,
    showDeleteAction: Boolean = false,
    enableCropping: Boolean = false,
    aspectRatioX: Int? = null,
    aspectRatioY: Int? = null
) : BaseSheet<ImagePickerViewModel> {

    @Composable
    override fun Content()

    val allowGallery: Boolean
    val allowCamera: Boolean
    val showDeleteAction: Boolean
    val enableCropping: Boolean
    val aspectRatioX: Int?
    val aspectRatioY: Int?
}