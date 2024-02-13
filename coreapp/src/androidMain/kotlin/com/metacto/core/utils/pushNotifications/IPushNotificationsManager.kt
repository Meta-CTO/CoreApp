package com.metacto.core.utils.pushNotifications

interface IPushNotificationsManager {
    fun getPushToken(): String?
    suspend fun forceGetPushToken(): String
    fun onPushTokenUpdated(newToken: String)
}