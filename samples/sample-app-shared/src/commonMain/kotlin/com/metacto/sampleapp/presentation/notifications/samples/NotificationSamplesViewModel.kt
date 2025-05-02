package com.metacto.sampleapp.presentation.notifications.samples

import com.metacto.sampleapp.presentation.base.BaseViewModel
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesContract.Effect
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesContract.Event
import com.metacto.sampleapp.presentation.notifications.samples.NotificationSamplesContract.State

class NotificationSamplesViewModel : BaseViewModel<State, Event, Effect>() {

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
