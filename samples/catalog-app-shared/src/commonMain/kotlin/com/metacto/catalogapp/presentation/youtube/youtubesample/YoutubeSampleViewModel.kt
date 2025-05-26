package com.metacto.catalogapp.presentation.youtube.youtubesample

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleContract.Effect
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleContract.Event
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleContract.State

class YoutubeSampleViewModel : BaseViewModel<State, Event, Effect>() {

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
