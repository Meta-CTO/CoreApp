package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class SplashContract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object ScreenAppeared : Event()
    }

    sealed class Effect : ViewSideEffect

    companion object {
        const val SPLASH_DELAY = 1500L
    }
}