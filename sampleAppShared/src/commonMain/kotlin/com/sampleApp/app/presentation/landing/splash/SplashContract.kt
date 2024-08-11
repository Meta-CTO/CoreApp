package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel

class SplashContract {

    data class State(
        val isInitialized: Boolean = false,
        val isWelcome: Boolean = false,
        val isVideoPlaying: Boolean = true,
        val isFullScreen: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data object ScreenAppeared : Event()
        data object ScreenDisposed : Event()
        data object ScheduleRepeatingNotification : Event()
        data object CancelScheduledNotification : Event()
        data object ClickMeClicked : Event()
        data object PlayerActionClicked : Event()
        data object NavigateToYoutube : Event()
        data class OnFullScreenChanged(val isFullScreen: Boolean) : Event()
    }

    sealed class Effect : ViewSideEffect

    companion object {
        const val SPLASH_DELAY = 1500L
        val DUMMY_OPTIONS = (0..50).map {
            PickerItemUIModel(it.toString(), "Item $it")
        }
    }
}