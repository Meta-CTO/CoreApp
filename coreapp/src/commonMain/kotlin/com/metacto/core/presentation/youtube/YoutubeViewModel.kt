package com.metacto.core.presentation.youtube

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.youtube.YoutubeContract.Event
import com.metacto.core.presentation.youtube.YoutubeContract.State
import com.metacto.core.presentation.youtube.YoutubeContract.Effect

class YoutubeViewModel : CoreViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(event.videoId)
        Event.BackClicked -> handleBackClick()
        is Event.OrientationChanged -> handleOrientationChanged(event.isLandscape)
    }

    fun init(videoId: String) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setState { copy(videoId = videoId) }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleBackClick() {
        navManager.goBack()
    }

    private fun handleOrientationChanged(isLandscape: Boolean) {
        setState { copy(isLandscape = isLandscape) }
    }
}