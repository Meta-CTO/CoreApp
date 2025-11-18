package com.metacto.catalogapp.presentation.datePicker

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import kotlinx.datetime.LocalDate

class DatePickerContract {

    data class State(
        val isInitialized: Boolean = false,
        val useNativePicker: Boolean = true,
        val selectedDate: LocalDate? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object TogglePickerType : Event()
        data class OnDateSelected(val date: LocalDate) : Event()
    }

    sealed class Effect : ViewSideEffect
}
