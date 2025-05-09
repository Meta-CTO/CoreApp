package com.metacto.catalogapp.presentation.imagePreloader.imagepreloader

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderContract.Effect
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderContract.Event
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderContract.State

class ImagePreloaderViewModel : BaseViewModel<State, Event, Effect>() {

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
