package com.sampleApp.app.presentation.test

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class TestContract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object BackClicked : Event()
        data object ClearAndOpenMainScreen : Event()
    }

    sealed class Effect : ViewSideEffect
}
