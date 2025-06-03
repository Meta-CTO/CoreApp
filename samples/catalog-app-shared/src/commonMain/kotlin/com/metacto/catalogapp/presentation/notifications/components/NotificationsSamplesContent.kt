package com.metacto.catalogapp.presentation.notifications.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.Event
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.notifications.INotificationManager
import com.metacto.core.notifications.Notification
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject
import kotlin.random.Random

@Composable
internal fun NotificationsSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val notificationManager = koinInject<INotificationManager>()
    val globalState = koinInject<IAppGlobalState>()
    val coroutineScope = rememberCoroutineScope()

    var lastScheduledNotificationId by remember { mutableStateOf<Int?>(null) }
    var currentPushToken by remember { mutableStateOf<String?>(null) }

    // Container column
    AppScreenColumn(
        title = "Notifications Samples",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        PrimaryFilledButton(
            text = "Schedule Notification (in 10s)",
            onClick = {
                val notificationId = Random.nextInt(1000, 2000)
                val notification = Notification.new(
                    id = notificationId,
                    title = "Test Notification $notificationId",
                    body = "This is a scheduled notification.",
                )
                val scheduleTime = Clock.System.now()
                    .plus(DateTimePeriod(seconds = 10), TimeZone.currentSystemDefault())
                    .toLocalDateTime(TimeZone.currentSystemDefault())

                try {
                    notificationManager.schedule(notification, scheduleTime)
                    lastScheduledNotificationId = notificationId
                    globalState.showSuccess("Notification $notificationId scheduled for $scheduleTime.")
                } catch (e: Throwable) {
                    globalState.showError("Failed to schedule notification: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Schedule Repeating Daily (14:30)",
            onClick = {
                val notificationId = Random.nextInt(2000, 3000)
                val notification = Notification.new(
                    id = notificationId,
                    title = "Daily Repeat $notificationId",
                    body = "This is a daily repeating notification for 14:30.",
                )
                try {
                    notificationManager.scheduleRepeating(notification, hourOfDay = 14, minute = 30)
                    lastScheduledNotificationId = notificationId
                    globalState.showSuccess("Notification $notificationId scheduled for daily 14:30.")
                } catch (e: Throwable) {
                    globalState.showError("Failed to schedule daily repeating: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Schedule Repeating Interval (1 min)",
            onClick = {
                val notificationId = Random.nextInt(3000, 4000)
                val notification = Notification.new(
                    id = notificationId,
                    title = "Interval Repeat $notificationId",
                    body = "This repeats every 1 minute.",
                )
                try {
                    notificationManager.scheduleRepeating(notification, intervalMinutes = 1)
                    lastScheduledNotificationId = notificationId
                    globalState.showSuccess("Notification $notificationId scheduled for 1 min interval.")
                } catch (e: Throwable) {
                    globalState.showError("Failed to schedule interval repeating: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Cancel Scheduled Notification",
            onClick = {
                lastScheduledNotificationId?.let {
                    try {
                        notificationManager.cancelScheduled(it)
                        globalState.showSuccess("Cancelled scheduled notification: $it")
                        lastScheduledNotificationId = null
                    } catch (e: Throwable) {
                        globalState.showError("Failed to cancel notification $it: ${e.message}")
                    }
                } ?: globalState.showError("No notification ID to cancel.")
            },
            isEnabled = lastScheduledNotificationId != null, // Corrected: Using the isEnabled parameter
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Remove Delivered Notification",
            onClick = {
                lastScheduledNotificationId?.let {
                    try {
                        notificationManager.removeDelivered(it)
                        globalState.showSuccess("Attempted to remove delivered notification: $it")
                    } catch (e: Throwable) {
                        globalState.showError("Failed to remove delivered $it: ${e.message}")
                    }
                } ?: globalState.showError("No notification ID to remove (using last scheduled ID for demo).")
            },
            isEnabled = lastScheduledNotificationId != null, // Corrected: Using the isEnabled parameter
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Remove All Delivered Notifications",
            onClick = {
                try {
                    notificationManager.removeAllDelivered()
                    globalState.showSuccess("Removed all delivered notifications.")
                } catch (e: Throwable) {
                    globalState.showError("Failed to remove all delivered: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Clear Badge Count",
            onClick = {
                try {
                    notificationManager.clearBadgeCount()
                    globalState.showSuccess("Badge count cleared.")
                } catch (e: Throwable) {
                    globalState.showError("Failed to clear badge count: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Get Push Token",
            onClick = {
                coroutineScope.launch {
                    try {
                        val token = notificationManager.getPushNotificationToken()
                        currentPushToken = token
                        if (token != null) {
                            globalState.showSuccess("Push Token: $token")
                        } else {
                            globalState.showError("Failed to get push token or token is null.")
                        }
                    } catch (e: Throwable) {
                        currentPushToken = null
                        globalState.showError("Error getting push token: ${e.message}")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        currentPushToken?.let { token ->
            Text(
                text = "Current Token: $token",
                modifier = Modifier.padding(top = spacings.spacing8)
            )
        }

        PrimaryFilledButton(
            text = "Delete Push Token",
            onClick = {
                coroutineScope.launch {
                    try {
                        notificationManager.deletePushNotificationToken()
                        currentPushToken = null
                        globalState.showSuccess("Push token deleted.")
                    } catch (e: Throwable) {
                        globalState.showError("Failed to delete push token: ${e.message}")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Subscribe to 'testTopic'",
            onClick = {
                coroutineScope.launch {
                    try {
                        notificationManager.subscribeToTopic("testTopic")
                        globalState.showSuccess("Subscribed to 'testTopic'.")
                    } catch (e: Throwable) {
                        globalState.showError("Failed to subscribe: ${e.message}")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Unsubscribe from 'testTopic'",
            onClick = {
                coroutineScope.launch {
                    try {
                        notificationManager.unSubscribeFromTopic("testTopic")
                        globalState.showSuccess("Unsubscribed from 'testTopic'.")
                    } catch (e: Throwable) {
                        globalState.showError("Failed to unsubscribe: ${e.message}")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )

        PrimaryFilledButton(
            text = "Setup Notification Listeners",
            onClick = {
                try {
                    notificationManager.onNewTokenListener { token ->
                        globalState.showSuccess("Listener: New Token: $token")
                        currentPushToken = token
                    }
                    notificationManager.onReceiveMessageNotification { title, body ->
                        globalState.showSuccess("Listener: Message: $title - $body")
                    }
                    notificationManager.onReceiveDataNotification { payload ->
                        globalState.showSuccess("Listener: Data: $payload")
                    }
                    notificationManager.onNotificationClicked { payload ->
                        globalState.showSuccess("Listener: Clicked: $payload")
                    }
                    globalState.showSuccess("Notification listeners have been set up.")
                } catch (e: Throwable) {
                    globalState.showError("Failed to set up listeners: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing16)
        )
    }
}