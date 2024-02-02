package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.sampleApp.app.MR
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
        println(
            "laaang: " + resourceProvider.getPluralString(
                MR.plurals.d_languages,
                1,
                1
            )
        )
        println(
            "laaang: " + resourceProvider.getPluralString(
                MR.plurals.d_languages,
                3,
                3
            )
        )

        // Observe item picker results
        observeItemPickerResults()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun observeItemPickerResults() {
        navManager.collectNavResult<ItemPickerSheet, PickerItem> {
            println("Item selected: $it")
            setState { copy(selectedItem = it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.ScreenAppeared -> {
        }

        Event.ScreenDisposed -> {
        }

        Event.TextClicked -> {
//            navManager.navigate(
//                SplashScreen(
//                    isWelcome = currentState.isWelcome.not()
//                )
//            )

            navManager.navigateToBottomSheet(
                ItemPickerSheet(
                    items = currentState.options,
                    selectedItem = currentState.selectedItem
                )
            )
        }

        Event.AnimClicked -> {
            showLoading(LoadingType.LottieBlocking())
        }
    }

    private fun checkUserState() {
    }
}