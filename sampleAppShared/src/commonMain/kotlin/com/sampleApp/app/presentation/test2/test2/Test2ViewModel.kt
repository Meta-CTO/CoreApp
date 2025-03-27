package com.sampleApp.app.presentation.test2.test2

import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.test2.test2.Test2Contract.Effect
import com.sampleApp.app.presentation.test2.test2.Test2Contract.Event
import com.sampleApp.app.presentation.test2.test2.Test2Contract.State

class Test2ViewModel : BaseViewModel<State, Event, Effect>() {

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
