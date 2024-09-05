package com.sampleApp.app.presentation.main

import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.forceUpdate.AppUpdateSource
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SnackBarType
import com.metacto.core.utils.Date
import com.metacto.core.utils.notificationManager.INotificationManager
import com.metacto.core.utils.notificationManager.Notification
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.main.MainContract.Effect
import com.sampleApp.app.presentation.main.MainContract.Event
import com.sampleApp.app.presentation.main.MainContract.State
import org.koin.core.component.inject

class MainViewModel : BaseViewModel<State, Event, Effect>() {
    private val appEnvironment by inject<CoreEnvironment>()
    private val notificationManager by inject<INotificationManager>()

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        is Event.ChangeTab -> {
            setState { copy(currentTab = event.index) }
        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        checkForUpdates()
        handleRemoteNotifications()

        // Update the flag
        setState { copy(isInitialized = true) }
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
            }
        )
    })

    private fun handleRemoteNotifications() {
        executeSilent({
            val pushToken = notificationManager.getPushNotificationToken()

            println("Push Token: $pushToken")
            globalState.snackBar(
                SnackBarParams(
                    message = "Push Token: $pushToken",
                    type = SnackBarType.SUCCESS
                )
            )
        })

        notificationManager.onNewTokenListener {
            println("New Token: $it")
            globalState.snackBar(
                SnackBarParams(
                    message = "New Token: $it",
                    type = SnackBarType.SUCCESS
                )
            )
        }

        notificationManager.onReceiveMessageNotification { title, body ->
            println("New Notification: $title, $body")
            globalState.snackBar(
                SnackBarParams(
                    message = "New Notification: $title, $body",
                    type = SnackBarType.SUCCESS
                )
            )

            notificationManager.schedule(
                notification = Notification.new(
                    title = "ssss: $title",
                    body = "body: $body"
                ),
                date = Date()
            )
        }

        notificationManager.onReceiveDataNotification { data ->
            println("New Data Notification: $data")
            globalState.snackBar(
                SnackBarParams(
                    message = "New Data Notification: $data",
                    type = SnackBarType.SUCCESS
                )
            )

            notificationManager.schedule(
                notification = Notification.new(
                    title = "Notification",
                    body = data.toString()
                ),
                date = Date()
            )
        }

        notificationManager.onNotificationClicked {
            println("Notification Clicked: $it")
            globalState.snackBar(
                SnackBarParams(
                    message = "Notification Clicked: $it",
                    type = SnackBarType.SUCCESS
                )
            )
        }
    }
}