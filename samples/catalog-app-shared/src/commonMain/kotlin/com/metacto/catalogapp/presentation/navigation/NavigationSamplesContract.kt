package com.metacto.catalogapp.presentation.navigation

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.navigation.NavScreen

internal interface NavigationSamplesContract {
    data class State(
        val isInitialized: Boolean = false,
        val isLoading: Boolean = false,
        val lastScreen: NavScreen? = null,
        val navigationHistory: List<String> = emptyList(),
        val currentScreenInfo: String = "",
        val hasScreenResult: Boolean? = null
    ) : ViewState

    sealed interface Event : ViewEvent {
        data object Init : Event
        data object NavigateToFirstSample : Event
        data object NavigateToSecondSample : Event
        data object NavigateToThirdSample : Event
        data object NavigateAndPopCurrent : Event
        data object ClearAndNavigate : Event
        data object PopToFirst : Event
        data object PopByTwo : Event
        data object GetLastScreen : Event
        data object CheckCurrentScreen : Event
        data object GoBack : Event
        data object NavigateWithResult : Event
        data object GoBackWithResult : Event
    }

    sealed interface Effect : ViewSideEffect {
        data object NavigationCompleted : Effect
        data class ScreenCheckCompleted(val info: String) : Effect
        data class ReceivedResult(val result: String) : Effect
    }
}