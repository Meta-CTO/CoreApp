package com.metacto.catalogapp.presentation.datePicker

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.datePicker.DatePickerContract.Effect
import com.metacto.catalogapp.presentation.datePicker.DatePickerContract.Event
import com.metacto.catalogapp.presentation.datePicker.DatePickerContract.State

class DatePickerViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.TogglePickerType -> togglePickerType()
        is Event.OnDateSelected -> onDateSelected(event.date)
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun togglePickerType() {
        setState { copy(useNativePicker = !useNativePicker) }
    }

    private fun onDateSelected(date: kotlinx.datetime.LocalDate) {
        setState { copy(selectedDate = date) }
    }
}
