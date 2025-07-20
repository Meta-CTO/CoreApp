package com.metacto.catalogapp.presentation.notifications

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class NotificationsSamplesContract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object EnablePushNotifications : Event()
        data object ClearPermissionState : Event()
    }

    sealed class Effect : ViewSideEffect
}
