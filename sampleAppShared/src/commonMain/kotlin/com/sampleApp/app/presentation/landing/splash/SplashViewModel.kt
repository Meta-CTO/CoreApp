package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
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
        println("laaang: " + resourceProvider.getPluralString(
            MR.plurals.d_languages,
            1,
            1
        ))
        println("laaang: " + resourceProvider.getPluralString(
            MR.plurals.d_languages,
            3,
            3
        ))

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
//            navManager.navigate(
//                SplashScreen(
//                    isWelcome = currentState.isWelcome.not()
//                )
//            )

            navManager.navigateToBottomSheet(
                ItemPickerSheet(
                    items = (0..50).map {
                        PickerItemUIModel(it.toString(), "Item $it")
                    },
                    selectedItem =  PickerItemUIModel("20", "Item 20")
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