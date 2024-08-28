package com.sampleApp.app.presentation.profile

import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.profile.ProfileContract.Effect
import com.sampleApp.app.presentation.profile.ProfileContract.Event
import com.sampleApp.app.presentation.profile.ProfileContract.State

class ProfileViewModel : BaseViewModel<State, Event, Effect>() {

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
