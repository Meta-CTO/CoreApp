package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.sampleApp.app.presentation.landing.splash.components.VideoItemInfo
import kotlinx.datetime.LocalDate

class SplashContract {

    data class State(
        val isInitialized: Boolean = false,
        val isWelcome: Boolean = false,
        val isVideoPlaying: Boolean = true,
        val selectedDate: LocalDate? = null,
        val currentVideo: VideoItemInfo = VideoItemInfo(
            url = "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            title = "Mahmoud Elshamy - Talking about the future of the world",
            artist = "Mahmoud Elshamy",
            artworkUrl = "https://mahmoudelshamy.com/index-assets/images/profile-2-250x250.png"
        )
    ) : ViewState

    sealed class Event : ViewEvent {
        data object ScreenAppeared : Event()
        data object ScreenDisposed : Event()
        data object ScheduleRepeatingNotification : Event()
        data object CancelScheduledNotification : Event()
        data object ClickMeClicked : Event()
        data object PlayerActionClicked : Event()
        data object NavigateToYoutube : Event()
        data object OnCalenderEventClicked:Event()
        data class SetCurrentVideo(val video: VideoItemInfo):Event()
    }

    sealed class Effect : ViewSideEffect

    companion object {
        const val SPLASH_DELAY = 1500L
        val DUMMY_OPTIONS = (0..50).map {
            PickerItemUIModel(it.toString(), "Item $it")
        }
    }
}