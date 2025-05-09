package com.metacto.catalogapp.presentation.imagePicker

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesContract.Effect
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesContract.Event
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesContract.State

class ImagePickerSamplesViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }
}
