package com.metacto.core.presentation.components.imagePicker

import androidx.compose.runtime.Composable

expect class ImagePicker {
    val enableCropping: Boolean
    val aspectRatioX: Int?
    val aspectRatioY: Int?

    @Composable
    fun registerPicker(onImagePicked: (ByteArray) -> Unit)

    fun pickFromGallery()

    fun captureUsingCamera()
}

@Composable
expect fun rememberImagePicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?
): ImagePicker