package com.metacto.core.utils.launchers

import kotlinx.datetime.LocalDateTime

interface IIntentLauncher {
    fun launchEmail(
        email: String,
        subject: String? = null,
        body: String? = null
    )

    fun launchStore(appId: String)

    fun shareText(text: String)

    fun launchPhone(phone: String)

    fun launchBrowser(url: String)

    suspend fun shareImage(imageUrl: String, text: String? = null)

    fun addEventToCalendar(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: LocalDateTime,
        eventEndTime: LocalDateTime
    )
}