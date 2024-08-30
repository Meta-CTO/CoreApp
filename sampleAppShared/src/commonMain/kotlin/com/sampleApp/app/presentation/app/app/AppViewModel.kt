package com.sampleApp.app.presentation.app.app

import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.sampleApp.app.presentation.app.app.AppContract.Effect
import com.sampleApp.app.presentation.app.app.AppContract.Event
import com.sampleApp.app.presentation.app.app.AppContract.State
import com.sampleApp.app.presentation.base.BaseViewModel
import org.koin.core.component.inject

class AppViewModel : BaseViewModel<State, Event, Effect>() {
    private val deepLinksManager by inject<IDeepLinkManager>()

    init {
        handleDeepLinks()
    }

    override fun setInitialState() = State

    override fun handleEvents(event: Event): Any = when(event) {
        else -> {}
    }

    private fun handleDeepLinks() = executeSilent({
        deepLinksManager.observeDeepLinks {
            showError("Received deep link: $it")
        }
    })
}
