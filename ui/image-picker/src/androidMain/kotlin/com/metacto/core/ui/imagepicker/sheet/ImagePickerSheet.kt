package com.metacto.core.ui.imagepicker.sheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.ui.base.CoreSheet
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.extensions.consume
import com.metacto.core.ui.imagepicker.MediaType
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
) : CoreSheet<ImagePickerViewModel>() {

    @Composable
    actual override fun Content() {
        // Get main objects
        val viewModel = rememberViewModel<ImagePickerViewModel>()
        val imagePicker = com.metacto.core.ui.imagepicker.rememberMediaPicker(
            includeData = true,
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

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                Event.Init(
                    allowGallery = allowGallery,
                    allowCamera = allowCamera,
                    showDeleteAction = showDeleteAction
                )
            )
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