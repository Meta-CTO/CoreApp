package com.metacto.core.utils.launchers

import kotlinx.datetime.LocalDateTime

interface IIntentLauncher {
    fun launchEmail(
        email: String,
        subject: String? = null,
        body: String? = null
    )

    fun launchAppInStore(appId: String)

    fun shareText(text: String)

    fun launchPhone(phone: String)

    fun launchBrowser(url: String)

    fun launchAppSettings()

    fun launchMap(latitude: Double, longitude: Double, name: String? = null)

    fun checkAppInstalled(appId: String): Boolean

    fun openDeepLink(link: String, onError: (() -> Unit)?): Boolean

    fun canHandleScheme(scheme: String, host: String? = null): Boolean

    suspend fun shareImage(imageUrl: String, text: String? = null)

    fun addEventToCalendar(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: LocalDateTime,
        eventEndTime: LocalDateTime
    )
}