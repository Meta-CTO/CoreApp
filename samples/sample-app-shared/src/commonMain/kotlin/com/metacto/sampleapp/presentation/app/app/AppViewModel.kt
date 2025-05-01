package com.metacto.sampleapp.presentation.app.app

import com.metacto.core.deepLink.IDeepLinkManager
import com.metacto.sampleapp.presentation.base.BaseViewModel
import org.koin.core.component.inject
import com.metacto.sampleapp.presentation.app.app.AppContract.State
import com.metacto.sampleapp.presentation.app.app.AppContract.Event
import com.metacto.sampleapp.presentation.app.app.AppContract.Effect

class AppViewModel : BaseViewModel<State, Event, Effect>() {
    private val deepLinksManager by inject<IDeepLinkManager>()

    init {
        handleDeepLinks()
    }

    override fun setInitialState() = State

    override fun handleEvents(event: Event): Any = when(event) {
        Event.Init -> {
        }
    }

    private fun handleDeepLinks() = executeSilent({
        deepLinksManager.observeDeepLinks {
            showError("Received deep link: $it")
        }
    })
}
