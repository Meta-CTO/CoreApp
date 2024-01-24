package com.metacto.core.presentation.imagePicker

import androidx.compose.runtime.Composable
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.getViewModel
import com.metacto.core.presentation.components.imagePicker.rememberImagePicker
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Effect
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.core.presentation.imagePicker.components.ImagePickerContent
import com.metacto.core.utils.extensions.consume

class ImagePickerSheet(
    allowGallery: Boolean = true,
    allowCamera: Boolean = true,
    showDeleteAction: Boolean = false,
    private val enableCropping: Boolean = false,
    private val aspectRatioX: Int? = null,
    private val aspectRatioY: Int? = null
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
    override fun Content() {
        // Create the image picker
        val imagePicker = rememberImagePicker(
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )

        // Handle side effects
        viewModel.effect.consume { effect ->
            when (effect) {
                Effect.PickImage -> imagePicker.pickFromGallery()
                Effect.CaptureImage -> imagePicker.captureUsingCamera()
            }
        }

        // Register image picker
        imagePicker.registerPicker {
            viewModel.setEvent(Event.PickImageResult(it))
        }

        // Render content
        ImagePickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}