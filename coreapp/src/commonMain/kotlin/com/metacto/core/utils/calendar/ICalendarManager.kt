package com.metacto.core.utils.calendar

interface ICalendarManager {

    suspend fun addEventToCalender(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: Long,
        eventEndTime: Long,
        onEventAdded: () -> Unit,
        onEventError: (error: String) -> Unit,
    )
}