package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.imagePicker.ImagePickerSheet
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.utils.DateHelper
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.metacto.core.utils.getDayOfWeek
import com.metacto.core.utils.launchers.IIntentLauncher
import com.metacto.core.utils.notificationManager.INotificationManager
import com.metacto.core.utils.notificationManager.Notification
import com.metacto.core.utils.parseDate
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Effect
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import com.sampleApp.app.presentation.landing.youtube.YoutubeScreen
import kotlinx.datetime.DayOfWeek
import org.koin.core.component.inject

class SplashViewModel(
    private val eventBroadcaster: EventBroadcaster,
    private val intentLauncher: IIntentLauncher,
    private val dateHelper: DateHelper,
    private val phoneNumberManager: IPhoneNumberManager
) : BaseViewModel<State, Event, Effect>() {
    private val notificationManager by inject<INotificationManager>()
    private var selectedPickerItem: PickerItemUIModel? = null

    fun init(isWelcome: Boolean) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setState { copy(isWelcome = isWelcome) }
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.SUNDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.MONDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.TUESDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.WEDNESDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.THURSDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.FRIDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.SATURDAY)}")

        setState { copy(selectedDate = "1993-09-09".parseDate("yyyy-MM-dd")) }

        navManager.collectNavResult<ItemPickerSheet, PickerItemUIModel> { pickedItem ->
            selectedPickerItem = pickedItem
        }

        // Update the flag
        setState { copy(isInitialized = true) }
    }


    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.ScreenAppeared -> {
        }

        Event.ScreenDisposed -> {
        }

        Event.ScheduleRepeatingNotification -> {
            val notification = Notification.new(
                id = 5001,
                title = "Scheduled notification title",
                body = "Scheduled notification description",
            )

//            notificationManager.scheduleRepeating(
//                notification = notification,
//                hourOfDay = 11,
//                minute = 3
//            )

            notificationManager.scheduleRepeating(
                notification = notification,
                intervalMinutes = 1
            )
        }

        Event.CancelScheduledNotification -> {
            notificationManager.cancelScheduled(5001)
        }

        Event.PlayerActionClicked -> {
            setState { copy(isVideoPlaying = isVideoPlaying.not()) }
        }

        Event.ClickMeClicked -> {
            setState { copy(selectedDate = "1993-09-09".parseDate("yyyy-MM-dd")) }

//            navManager.navigateToBottomSheet(
//                ImagePickerSheet(
//                    enableCropping = true,
//                    aspectRatioX = 1,
//                    aspectRatioY = 1
//                )
//            )
//            val isValid = phoneNumberManager.isValidPhoneNumber("01121980284", "EG")
//            showError("Is valid: $isValid")

//            navManager.navigateToBottomSheet(
//                ItemPickerSheet(
//                    selectedItem = selectedPickerItem,
//                    canSearch = true,
//                    items = listOf(
//                        PickerItemUIModel("key1", "title1"),
//                        PickerItemUIModel("key2", "title2"),
//                        PickerItemUIModel("key3", "title3"),
//                        PickerItemUIModel("key4", "title4"),
//                        PickerItemUIModel("key5", "title5"),
//                        PickerItemUIModel("key6", "title6"),
//                        PickerItemUIModel("key7", "title7"),
//                    )
//                )
//            )
        }

        Event.NavigateToYoutube -> {
            navManager.navigate(YoutubeScreen())
        }
    }
}