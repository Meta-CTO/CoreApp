package com.sampleApp.app.presentation.testsheet1

import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.testsheet1.TestSheet1Contract.Effect
import com.sampleApp.app.presentation.testsheet1.TestSheet1Contract.Event
import com.sampleApp.app.presentation.testsheet1.TestSheet1Contract.State

class TestSheet1ViewModel : BaseViewModel<State, Event, Effect>() {

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
