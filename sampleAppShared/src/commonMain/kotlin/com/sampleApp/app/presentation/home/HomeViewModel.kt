package com.sampleApp.app.presentation.home

import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.home.HomeContract.Companion.VIDEOS_LIST
import com.sampleApp.app.presentation.home.HomeContract.Effect
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.test.TestScreen
import com.sampleApp.app.presentation.youtube.YoutubeScreen
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime


class HomeViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()

        Event.NavToYoutubeScreen -> {
            navManager.navigate(YoutubeScreen())
        }

        Event.NavToTestScreen -> {
            navManager.navigate(TestScreen)
        }

        is Event.ChangeCurrentVideo -> {
            setState { copy(currentVideo = VIDEOS_LIST[event.index]) }
            currentState.videoController?.play()
            Unit
        }

        is Event.VideoPlayerControllerCreated -> {
            setState { copy(videoController = event.controller) }
        }

        is Event.OpenPicker -> {
            navManager.navigateToBottomSheet(
                ItemPickerSheet(
                    selectedItem = currentState.pickedItem,
                    items = (0..20).map {
                        PickerItemUIModel(
                            key = it.toString(),
                            title = "Item $it"
                        )
                    }
                )
            )
        }

        Event.AddToCalendar -> {
            val currentInstant = Clock.System.now()

            // Convert the Instant to LocalDateTime using the system's default time zone
            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            // Add 1 day to get tomorrow's Instant
            val tomorrowInstant = currentInstant.plus(DateTimePeriod(days = 1), TimeZone.currentSystemDefault())

            // Convert tomorrow's Instant back to LocalDateTime
            val tomorrowDateTime = tomorrowInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            // Add 2 days to get the day after tomorrow's Instant
            val dayAfterTomorrowInstant = currentInstant.plus(DateTimePeriod(days = 2), TimeZone.currentSystemDefault())

            // Convert the day after tomorrow's Instant back to LocalDateTime
            val dayAfterTomorrowDateTime = dayAfterTomorrowInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            intentLauncher.addEventToCalendar(
                "event title", "event description",
                tomorrowDateTime,dayAfterTomorrowDateTime
            )
        }

        Event.OpenBrowser -> {
            intentLauncher.launchBrowser("https://www.facebook.com/")
        }

        Event.OpenPhone -> {
            intentLauncher.launchPhone("+201555056563")
        }

        Event.OpenStore -> {
            intentLauncher.launchStore("")
        }

        Event.ShareEmail -> {
            intentLauncher.launchEmail("uni.fareed@gmail.com", "Test subject", "test body")
        }

        Event.ShareImage -> {
            shareImage()
        }

        Event.ShareText -> {
            intentLauncher.shareText("Hello share text")
        }
    }

    private fun shareImage() = executeCatching(
        loadingType = super.defaultLoadingType,
        block = {
            intentLauncher.shareImage("https://upload.wikimedia.org/wikipedia/commons/4/40/Image_test.png?20141030190340")
        }
    )

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        observeItemPickerResults()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun observeItemPickerResults() {
        navManager.collectNavResult<ItemPickerSheet, PickerItemUIModel> { pickedItem ->
            setState { copy(pickedItem = pickedItem) }
        }
    }
}
