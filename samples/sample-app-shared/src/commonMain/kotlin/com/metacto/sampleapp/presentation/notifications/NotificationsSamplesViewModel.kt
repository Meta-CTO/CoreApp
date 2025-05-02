package com.metacto.sampleapp.presentation.notifications

import com.metacto.sampleapp.presentation.base.BaseViewModel
import com.metacto.sampleapp.presentation.notifications.NotificationsSamplesContract.Effect
import com.metacto.sampleapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.sampleapp.presentation.notifications.NotificationsSamplesContract.State

class NotificationsSamplesViewModel : BaseViewModel<State, Event, Effect>() {

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
