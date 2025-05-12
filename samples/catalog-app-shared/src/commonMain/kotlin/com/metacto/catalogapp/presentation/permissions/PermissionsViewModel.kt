package com.metacto.catalogapp.presentation.permissions

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.permissions.PermissionsContract.Effect
import com.metacto.catalogapp.presentation.permissions.PermissionsContract.Event
import com.metacto.catalogapp.presentation.permissions.PermissionsContract.State

class PermissionsViewModel : BaseViewModel<State, Event, Effect>() {

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
