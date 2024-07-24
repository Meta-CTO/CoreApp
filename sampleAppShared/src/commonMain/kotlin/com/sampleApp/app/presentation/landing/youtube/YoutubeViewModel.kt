package com.sampleApp.app.presentation.landing.youtube

import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.youtube.YoutubeContract.Effect
import com.sampleApp.app.presentation.landing.youtube.YoutubeContract.Event
import com.sampleApp.app.presentation.landing.youtube.YoutubeContract.State

class YoutubeViewModel : BaseViewModel<State, Event, Effect>() {

    fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }


    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.CtaClicked -> {

        }
    }
}