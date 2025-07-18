package com.metacto.core.ui.imagepicker

import androidx.compose.runtime.Composable

expect class MediaPicker {
    val enableCropping: Boolean
    val aspectRatioX: Int?
    val aspectRatioY: Int?
    val includeData: Boolean

    @Composable
    fun registerPicker(onMediaPicked: (MediaInfo) -> Unit)

    fun pickFromGallery(mediaTypes: List<MediaType>)

    fun captureUsingCamera()
    
    internal fun cleanup()
}

@Composable
expect fun rememberMediaPicker(
    enableCropping: Boolean = false,
    aspectRatioX: Int? = null,
    aspectRatioY: Int? = null,
    includeData: Boolean = true
): MediaPicker