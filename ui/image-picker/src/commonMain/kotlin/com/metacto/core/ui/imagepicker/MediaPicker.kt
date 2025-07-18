package com.metacto.core.ui.imagepicker

import androidx.compose.runtime.Composable

expect class MediaPicker {
    val enableCropping: Boolean
    val aspectRatioX: Int?
    val aspectRatioY: Int?

    @Composable
    fun registerPicker(onMediaPicked: (MediaInfo) -> Unit)

    fun pickFromGallery(mediaTypes: List<MediaType>)

    fun captureUsingCamera()
}

@Composable
expect fun rememberMediaPicker(
    enableCropping: Boolean = false,
    aspectRatioX: Int? = null,
    aspectRatioY: Int? = null
): MediaPicker