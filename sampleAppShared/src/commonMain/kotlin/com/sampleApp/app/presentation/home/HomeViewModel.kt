package com.sampleApp.app.presentation.home

import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.home.HomeContract.Companion.VIDEOS_LIST
import com.sampleApp.app.presentation.home.HomeContract.Effect
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.youtube.YoutubeScreen

class HomeViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()

        Event.NavToYoutubeScreen -> {
            navManager.navigate(YoutubeScreen())
        }

        is Event.ChangeCurrentVideo -> {
            setState { copy(currentVideo = VIDEOS_LIST[event.index]) }
        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }
}
