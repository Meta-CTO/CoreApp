package com.sampleApp.app.presentation.main

import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.forceUpdate.AppUpdateSource
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.main.MainContract.Effect
import com.sampleApp.app.presentation.main.MainContract.Event
import com.sampleApp.app.presentation.main.MainContract.State
import org.koin.core.component.inject

class MainViewModel : BaseViewModel<State, Event, Effect>() {
    private val appEnvironment by inject<CoreEnvironment>()

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        is Event.ChangeTab -> {
            setState { copy(currentTab = event.index) }
        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        checkForUpdates()

        // Update the flag
        setState { copy(isInitialized = true) }
    }


    private fun checkForUpdates() = executeSilent({
        checkAppUpdates(
            appUpdateSource = AppUpdateSource.STRAPI_CONFIGS,
            title = "Ahmed",
            showTitle = true,
            onProceedAction = {
                // TODO will navigate to next screen
            },
            onSkipUpdateClick = {
                // to handle the skip update action if needed
            },
            onUpdateClick = {
                intentLauncher.launchStore(appId = appEnvironment.iosAppStoreId)
            }
        )
    })
}