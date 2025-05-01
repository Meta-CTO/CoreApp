package com.metacto.sampleapp.presentation.main

import com.metacto.sampleapp.presentation.base.BaseViewModel
import com.metacto.sampleapp.presentation.main.MainContract.Effect
import com.metacto.sampleapp.presentation.main.MainContract.Event
import com.metacto.sampleapp.presentation.main.MainContract.State

class MainViewModel : BaseViewModel<State, Event, Effect>() {

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
