package com.metacto.core.presentation.imagePicker

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class ImagePickerContract {

    data class State(
        val isInitialized: Boolean = false,
        val allowGallery: Boolean = false,
        val allowCamera: Boolean = false,
        val showDeleteAction: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(
            val allowGallery: Boolean,
            val allowCamera: Boolean,
            val showDeleteAction: Boolean
        ) : Event()

        data object CloseClicked : Event()
        data object PickFromGalleryClicked : Event()
        data object CaptureUsingCameraClicked : Event()
        data class PickImageResult(val bytes: ByteArray) : Event()
        data object DeleteCurrentPhotoClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        data object PickImage : Effect()
        data object CaptureImage : Effect()
    }
}