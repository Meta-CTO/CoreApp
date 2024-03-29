package com.sampleApp.app.presentation.landing.splash

import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.metacto.core.presentation.imagePicker.ImagePickerSheet
import com.metacto.core.presentation.imagePicker.models.ImagePickerResult
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.presentation.models.ImageUIModel
import com.metacto.core.utils.Date
import com.metacto.core.utils.DateHelper
import com.metacto.core.utils.dateFromTimestamp
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.metacto.core.utils.launchers.IIntentLauncher
import com.metacto.core.utils.notificationManager.INotificationManager
import com.metacto.core.utils.notificationManager.Notification
import com.metacto.core.utils.toMillis
import com.sampleApp.app.MR
import com.sampleApp.app.domain.events.UserEvent
import com.sampleApp.app.presentation.components.BaseViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Effect
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import org.koin.core.component.inject

class SplashViewModel(
    private val eventBroadcaster: EventBroadcaster,
    private val intentLauncher: IIntentLauncher,
    private val dateHelper: DateHelper
) : BaseViewModel<State, Event, Effect>() {
    private var clickCount = 0
    private val notificationManager by inject<INotificationManager>()

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
        observeImagePickerResults()

        if (isWelcome.not()) {
            executeSilent({
                eventBroadcaster.subscribeToEvent<UserEvent.UserDeleted>(UserEvent.UserDeleted.eventName) {
                    println("user deleted: $it")
                }
            })

            executeSilent({
                eventBroadcaster.subscribeToEvent<UserEvent.UserUpdated>(UserEvent.UserUpdated.eventName) {
                    println("user updated: ${it.userName}")
                }
            })

            executeSilent({
                eventBroadcaster.subscribeToEvent<UserEvent.UserAdded>(UserEvent.UserAdded.eventName) {
                    println("user added: ${it.user}")
                }
            })
        }

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
            globalState.snackBar(
                SnackBarParams(
                    message = "Text clicked",
                    type = SnackBarType.SUCCESS
                )
            )
//            println("Date formatted: " + DateHelper.timestampToReadableDate(1708286230001))

            val date = dateHelper.stringToDate("1993-01-01", "yyyy-MM-dd")
            println("The years difference: " + dateHelper.getElapsedYears(date))

//            navManager.navigate(
//                destination = SplashScreen(
//                    isWelcome = currentState.isWelcome
//                ),
//                behaviour = NavigateBehaviour.KeepIfCurrent
//            )

//            executeCatching({
//                permissionManager.grantPermission(Permission.CONTACTS)
//                println("Graaaaaanteeeeed")
//            })

//            val options = listOf(
//                OptionUIModel(
//                    title = "Option 1"
//                ),
//                OptionUIModel(
//                    title = "Option 2"
//                ),
//                OptionUIModel(
//                    title = "Option 3"
//                ),
//                OptionUIModel(
//                    title = "Option 4"
//                ),
//            )
//            navManager.navigateToBottomSheet(
//                OptionsSheet(
//                    options = options
//                )
//            )

//            when {
//                clickCount == 0 -> {
//                    eventBroadcaster.notify(UserEvent.UserDeleted())
//                }
//
//                clickCount == 1 -> {
//                    eventBroadcaster.notify(UserEvent.UserUpdated("shamy updated"))
//                }
//
//                clickCount == 2 -> {
//                    eventBroadcaster.notify(UserEvent.UserAdded(TestUserModel("naaame", 3, true)))
//                }
//
//                else -> {
//                    if (currentState.isWelcome) {
//                        navManager.clearAndNavigate(SplashScreen(isWelcome = true))
//                    } else {
//                        navManager.navigate(SplashScreen(isWelcome = true))
//                    }
//                }
//            }
//
//            clickCount++

//            navManager.navigate(
//                SplashScreen(
//                    isWelcome = currentState.isWelcome.not()
//                )
//            )

//            navManager.navigateToBottomSheet(
//                ItemPickerSheet(
//                    items = currentState.options,
//                    selectedItem = currentState.selectedItem
//                )
//            )
        }

        Event.AnimClicked -> {
            showLoading(LoadingType.LottieBlocking())
        }

        Event.SendEmailClicked -> {
            intentLauncher.launchEmail(
                email = "shamyyoun@gmail.com",
                subject = "Subject",
                body = "Body"
            )
        }

        Event.PhoneDialClicked -> {
            intentLauncher.launchPhone("+971526900377")
        }

        Event.ShareTextClicked -> {
            intentLauncher.launchShareText("Hello, this is a test text")
        }

        Event.PickImageClicked -> {
            navManager.navigateToBottomSheet(
                ImagePickerSheet(
                    showDeleteAction = true,
                    enableCropping = true,
                )
            )
        }

        Event.ShareHelloWorldClicked -> {
            intentLauncher.launchShareText("Hello World!")
        }

        Event.ShareParrotClub1 -> {
            intentLauncher.launchShareText("https://parrotclub.co")
        }

        Event.ShareParrotClub2 -> {
            intentLauncher.launchShareText("www.parrotclub.co")
        }

        Event.RemoveAllNotifications -> {
            notificationManager.removeAll()
        }

        Event.ShowNotificationLater -> {
            val notification = Notification.new(
                id = 5001,
                title = "Later notification title",
                description = "Later notification description",
            )

            val date = dateFromTimestamp(Date().toMillis() + 1000 * 10)
            notificationManager.schedule(notification, date)
        }

        Event.ScheduleRepeatingNotification -> {
            val notification = Notification.new(
                id = 5001,
                title = "Scheduelled notification title",
                description = "Scheduelled notification description",
            )

            notificationManager.scheduleRepeating(
                notification = notification,
                hourOfDay = 0,
                minute = 3
            )
        }

        Event.RemoveRepeatingNotification -> {
            notificationManager.cancelScheduled(5001)
        }

        Event.ShowNotificationNow -> {
            val notification = Notification.new(
                title = "Now notification title",
                description = "Now notification description",
            )
            notificationManager.show(notification)
        }
    }

    private fun observeImagePickerResults() {
        navManager.collectNavResult<ImagePickerSheet, ImagePickerResult> {
            when (it) {
                ImagePickerResult.Cancelled -> {}
                ImagePickerResult.ImageDeleted -> setState { copy(image = null) }
                is ImagePickerResult.ImagePicked -> setState { copy(image = ImageUIModel(bytes = it.bytes)) }
            }
        }
    }

    private fun checkUserState() {
    }
}