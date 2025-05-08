package com.metacto.catalogapp.presentation.files

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.Effect
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.Event
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.State
import com.metacto.core.files.IFileManager
import org.koin.core.component.inject

class FilesSamplesViewModel : BaseViewModel<State, Event, Effect>() {
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
