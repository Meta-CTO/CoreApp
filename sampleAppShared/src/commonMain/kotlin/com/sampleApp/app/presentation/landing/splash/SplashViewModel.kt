package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.imagePicker.ImagePickerSheet
import com.metacto.core.presentation.imagePicker.models.ImagePickerResult
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
        observeImagePickerResults()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun observeImagePickerResults() {
        navManager.collectNavResult<ImagePickerSheet, ImagePickerResult> {
            when(it) {
                ImagePickerResult.Cancelled -> {}
                ImagePickerResult.ImageDeleted -> {}
                is ImagePickerResult.ImagePicked -> {
                    setState { copy(imageBytes = it.bytes) }
                }
            }
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
                ImagePickerSheet(
                    enableCropping = true
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