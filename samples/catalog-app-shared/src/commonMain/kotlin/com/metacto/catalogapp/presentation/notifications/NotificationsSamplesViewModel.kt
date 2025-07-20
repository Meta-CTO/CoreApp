package com.metacto.catalogapp.presentation.notifications

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Effect
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.State
import com.metacto.core.ui.globalState.models.LoadingType
import com.metacto.core.ui.permissions.IPermissionManager
import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.exceptions.DeniedAlwaysException
import com.metacto.kmm.logger.Logger
import org.koin.core.component.inject

class NotificationsSamplesViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.EnablePushNotifications -> handleEnablePushNotificationClick()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init - Don't automatically request permission, let user trigger it manually
        
        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleEnablePushNotificationClick() = executeCatching(
        loadingType = LoadingType.NoLoading,
        block = {
            permissionManager.requestPermission(Permission.REMOTE_NOTIFICATION)
            Logger("handleEnablePushNotificationClick").log("Notifications enabled")
        },
        shouldShowErrorMessage = { throwable ->
            if (throwable is DeniedAlwaysException) {
                Logger("handleEnablePushNotificationClick").log("DeniedAlwaysException")
            }
            false
        }
    )
}
