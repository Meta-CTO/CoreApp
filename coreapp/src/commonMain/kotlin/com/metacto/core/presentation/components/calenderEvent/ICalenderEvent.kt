package com.metacto.core.presentation.components.calenderEvent

interface ICalenderEvent {

    suspend fun addEventToCalender(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: Long,
        eventEndTime: Long
    ): CalenderEventStatus
}