package com.metacto.core.presentation.youtube

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.youtube.YoutubeContract.Effect
import com.metacto.core.presentation.youtube.YoutubeContract.Event
import com.metacto.core.presentation.youtube.YoutubeContract.State

class YoutubeViewModel : CoreViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(
            event.videoId,
            event.shouldAutoPlay,
            event.showControls,
            event.showFullScreenButton
        )

        Event.BackClicked -> handleBackClick()
        is Event.OrientationChanged -> handleOrientationChanged(event.isLandscape)
    }

    fun init(
        videoId: String,
        shouldAutoPlay: Boolean,
        showControls: Boolean,
        showFullScreenButton: Boolean
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setState {
            copy(
                videoId = videoId,
                showControls = showControls,
                showFullScreenButton = showFullScreenButton,
                shouldAutoPlay = shouldAutoPlay
            )
        }

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