package com.metacto.core.presentation.imagePicker

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Effect
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.core.presentation.imagePicker.ImagePickerContract.State
import com.metacto.core.presentation.imagePicker.models.ImagePickerResult


class ImagePickerViewModel : CoreViewModel<State, Event, Effect>() {

    fun init(
        allowGallery: Boolean,
        allowCamera: Boolean,
        showDeleteAction: Boolean
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Update state
        setState {
            copy(
                allowGallery = allowGallery,
                allowCamera = allowCamera,
                showDeleteAction = showDeleteAction
            )
        }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.CloseClicked -> navManager.goBack()
        Event.PickFromGalleryClicked -> handlePickFromGalleryClick()
        Event.CaptureUsingCameraClicked -> handleCaptureUsingCameraClick()
        is Event.PickImageResult -> handlePickImageResult(event.bytes)
        Event.DeleteCurrentPhotoClicked -> handleDeleteCurrentPhotoClick()
    }

    private fun handlePickFromGalleryClick() {
        setEffect { Effect.PickImage }
    }

    private fun handleCaptureUsingCameraClick() {
        setEffect { Effect.CaptureImage }
    }

    private fun handlePickImageResult(bytes: ByteArray) {
        navManager.sendResult(
            source = ImagePickerSheet::class.simpleName,
            result = ImagePickerResult.ImagePicked(bytes)
        )
        navManager.goBack()
    }

    private fun handleDeleteCurrentPhotoClick() {
        navManager.sendResult(
            source = ImagePickerSheet::class.simpleName,
            result = ImagePickerResult.ImageDeleted
        )
        navManager.goBack()
    }
}