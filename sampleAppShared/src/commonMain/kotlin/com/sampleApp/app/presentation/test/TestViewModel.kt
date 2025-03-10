package com.sampleApp.app.presentation.test

import com.metacto.core.presentation.youtube.YoutubeScreen
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.main.MainScreen
import com.sampleApp.app.presentation.test.TestContract.Effect
import com.sampleApp.app.presentation.test.TestContract.Event
import com.sampleApp.app.presentation.test.TestContract.State

class TestViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.BackClicked -> navManager.goBack()
        Event.ClearAndOpenMainScreen -> navManager.clearAndNavigate(
            MainScreen()
        )
        Event.CheckMainScreenClicked -> handleCheckMainScreenClick()
        Event.CheckYoutubeScreenClicked -> handleCheckYoutubeScreenClick()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleCheckMainScreenClick() = executeSilent({
//        val exists = navManager.checkScreenByClass(MainScreen::class)
        val exists = navManager.checkScreenByTag("MainScreen")
        showError("MainScreen exists: $exists")
    })

    private fun handleCheckYoutubeScreenClick() = executeSilent({
//        val exists = navManager.checkScreenByClass(YoutubeScreen::class)
        val exists = navManager.checkScreenByTag("YoutubeScreen")
        showError("YoutubeScreen exists: $exists")
    })
}
