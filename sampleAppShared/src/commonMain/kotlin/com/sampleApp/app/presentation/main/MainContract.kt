package com.sampleApp.app.presentation.main

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class MainContract {

    data class State(
        val isInitialized: Boolean = false,
        val currentTab : Int = 0
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data class ChangeTab(val index: Int) : Event()
    }

    sealed class Effect : ViewSideEffect
}