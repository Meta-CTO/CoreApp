package com.metacto.core.presentation.imagePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.components.imagePicker.rememberImagePicker
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Effect
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.core.presentation.imagePicker.components.ImagePickerContent
import com.metacto.core.utils.extensions.consume

class ImagePickerSheet(
    private val allowGallery: Boolean = true,
    private val allowCamera: Boolean = true,
    private val showDeleteAction: Boolean = false,
    private val enableCropping: Boolean = false,
    private val aspectRatioX: Int? = null,
    private val aspectRatioY: Int? = null
) : BaseSheet<ImagePickerViewModel>() {

    @Composable
    override fun Content() {
        // Create main objects
        val viewModel = rememberViewModel<ImagePickerViewModel>()
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

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.init(
                allowGallery = allowGallery,
                allowCamera = allowCamera,
                showDeleteAction = showDeleteAction
            )
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