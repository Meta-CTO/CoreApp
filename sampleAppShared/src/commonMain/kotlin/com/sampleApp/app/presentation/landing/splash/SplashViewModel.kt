package com.sampleApp.app.presentation.landing.splash

import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Effect
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State

class SplashViewModel : BaseViewModel<State, Event, Effect>() {

    fun init(isWelcome: Boolean) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setState { copy(isWelcome = isWelcome) }
        checkUserState()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.ScreenAppeared -> {
        }

        Event.ScreenDisposed -> {
        }

        Event.TextClicked -> {
            navManager.navigate(
                SplashScreen(
                    isWelcome = currentState.isWelcome.not()
                )
            )
        }
    }

    private fun checkUserState() {
    }
}