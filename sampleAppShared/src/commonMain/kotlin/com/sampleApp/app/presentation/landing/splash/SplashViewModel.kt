package com.sampleApp.app.presentation.landing.splash

import com.sampleApp.app.presentation.app.globalState.models.AppBackgroundType
import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Effect
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State

class SplashViewModel : BaseViewModel<State, Event, Effect>() {

    fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        checkUserState()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.ScreenAppeared -> handleScreenAppear()
    }

    private fun handleScreenAppear() {
        globalState.updateAppBackground(AppBackgroundType.PRIMARY)
    }

    private fun checkUserState() {

    }
}