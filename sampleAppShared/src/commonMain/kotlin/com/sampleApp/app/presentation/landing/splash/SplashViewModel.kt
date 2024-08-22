package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.components.calenderEvent.CalenderEventStatus
import com.metacto.core.presentation.components.calenderEvent.ICalendarManager
import com.metacto.core.presentation.components.wheelPicker.datetime.now
import com.metacto.core.presentation.globalState.models.SuccessPopupParams
import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.forceUpdate.AppUpdateSource
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.utils.DateHelper
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.metacto.core.utils.getDayOfWeek
import com.metacto.core.utils.notificationManager.INotificationManager
import com.metacto.core.utils.notificationManager.Notification
import com.metacto.core.utils.parseDate
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.metacto.core.utils.toEpochMilliseconds
import com.metacto.core.utils.toInstant
import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Effect
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import com.sampleApp.app.presentation.landing.youtube.YoutubeScreen
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.inject

class SplashViewModel(
    private val eventBroadcaster: EventBroadcaster,
    private val intentLauncher: IIntentLauncher,
    private val iCalendarManager: ICalendarManager,
    private val dateHelper: DateHelper,
    private val phoneNumberManager: IPhoneNumberManager,
    private val appEnvironment: CoreEnvironment
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
        println(
            "Day of week: ${
                dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.WEDNESDAY)
            }"
        )
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.THURSDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.FRIDAY)}")
        println("Day of week: ${dateHelper.getCurrentLocalDate().getDayOfWeek(DayOfWeek.SATURDAY)}")

        setState { copy(selectedDate = "1993-09-09".parseDate("yyyy-MM-dd")) }

        navManager.collectNavResult<ItemPickerSheet, PickerItemUIModel> { pickedItem ->
            selectedPickerItem = pickedItem
        }

        checkForUpdates()
        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun sendCalenderEvent() = executeCatching({
        val calenderTime = Clock.System.now().toLocalDateTime(
            TimeZone.currentSystemDefault()
        ).toInstant().toEpochMilliseconds()

        val addedCalender = iCalendarManager.addEventToCalender(
            eventTitle = "New Event Title ",
            eventDescription = "new Event description",
            eventStartTime = calenderTime,
            eventEndTime = calenderTime + (60 * 60 * 1000)
        )

        delay(2000)
        if (addedCalender == CalenderEventStatus.EVENT_ADDED) {
            globalState.successPopup(
                params =
                SuccessPopupParams(
                    title = "Event Added",
                    body = "Event Added Successfully"
                )
            )
        }
    })


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
                notification = notification, intervalMinutes = 1
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

        Event.OnCalenderEventClicked -> sendCalenderEvent()
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
            })
    })
}