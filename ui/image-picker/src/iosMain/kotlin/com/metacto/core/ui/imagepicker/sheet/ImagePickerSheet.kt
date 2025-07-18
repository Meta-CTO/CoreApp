package com.metacto.core.ui.imagepicker.sheet

import androidx.compose.runtime.Composable
import com.metacto.core.ui.base.BaseSheet
import com.metacto.core.ui.base.getViewModel
import com.metacto.core.ui.extensions.consume
import com.metacto.core.ui.imagepicker.MediaType
import com.metacto.core.ui.imagepicker.rememberMediaPicker
import com.metacto.core.ui.imagepicker.sheet.ImagePickerContract.Effect
import com.metacto.core.ui.imagepicker.sheet.ImagePickerContract.Event
import com.metacto.core.ui.imagepicker.sheet.components.ImagePickerContent

actual class ImagePickerSheet actual constructor(
    actual val allowGallery: Boolean,
    actual val allowCamera: Boolean,
    actual val showDeleteAction: Boolean,
    actual val enableCropping: Boolean,
    actual val aspectRatioX: Int?,
    actual val aspectRatioY: Int?
) : BaseSheet<ImagePickerViewModel>() {

    private val viewModel = getViewModel<ImagePickerViewModel>()

    init {
        // Init view model
        viewModel.setEvent(
            Event.Init(
                allowGallery = allowGallery,
                allowCamera = allowCamera,
                showDeleteAction = showDeleteAction
            )
        )
    }

    @Composable
    actual override fun Content() {
        // Create the image picker
        val imagePicker = rememberMediaPicker(
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )

        // Handle side effects
        viewModel.effect.consume { effect ->
            when (effect) {
                Effect.PickImage -> imagePicker.pickFromGallery(listOf(MediaType.Image))
                Effect.CaptureImage -> imagePicker.captureUsingCamera()
            }
        }

        // Register image picker
        imagePicker.registerPicker {
            viewModel.setEvent(Event.PickMediaResult(it))
        }

        // Render content
        ImagePickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}