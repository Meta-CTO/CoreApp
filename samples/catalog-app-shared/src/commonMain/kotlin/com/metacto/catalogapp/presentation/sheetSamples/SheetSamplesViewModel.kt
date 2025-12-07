package com.metacto.catalogapp.presentation.sheetSamples

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesContract.Effect
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesContract.Event
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesContract.State

class SheetSamplesViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        is Event.OnSheetResult -> onSheetResult(event.result)
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun onSheetResult(result: String) {
        setState { copy(resultText = result) }
    }
}
