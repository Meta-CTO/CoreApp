package com.metacto.core.app

import com.metacto.core.CoreEnvironment
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

actual fun onApplicationStartPlatformSpecific(
    coreEnvironment: CoreEnvironment
) {

    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Android(
            notificationIconResId = coreEnvironment.notificationIconResId,
            showPushNotification = true,
            notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData()
        )
    )
}