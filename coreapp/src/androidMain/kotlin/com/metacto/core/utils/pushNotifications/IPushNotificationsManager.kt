package com.metacto.core.utils.pushNotifications

interface IPushNotificationsManager {
    fun getPushToken(): String?
    fun onPushTokenUpdated(newToken: String)
}