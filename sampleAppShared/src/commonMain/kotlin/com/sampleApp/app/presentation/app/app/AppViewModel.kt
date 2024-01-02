package com.sampleApp.app.presentation.app.app

import com.sampleApp.app.presentation.app.app.AppContract.Effect
import com.sampleApp.app.presentation.app.app.AppContract.Event
import com.sampleApp.app.presentation.app.app.AppContract.State
import com.sampleApp.app.presentation.components.BaseViewModel

class AppViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State

    override fun handleEvents(event: Event): Any = when(event) {
        else -> {}
    }
}
