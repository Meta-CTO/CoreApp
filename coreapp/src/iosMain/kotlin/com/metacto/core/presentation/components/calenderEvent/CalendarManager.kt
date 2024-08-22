package com.metacto.core.presentation.components.calenderEvent

import com.metacto.core.utils.dateFromTimestamp
import com.metacto.core.utils.extensions.contains
import kotlinx.cinterop.ExperimentalForeignApi
import platform.EventKit.EKEntityType
import platform.EventKit.EKEvent
import platform.EventKit.EKEventStore
import platform.EventKit.EKSpan

class CalendarManager : ICalendarManager {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun addEventToCalender(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: Long,
        eventEndTime: Long
    ): CalenderEventStatus {
        val eventStore = EKEventStore()

        var status: CalenderEventStatus = CalenderEventStatus.EVENT_NOT_ADDED

        eventStore.requestAccessToEntityType(EKEntityType.EKEntityTypeEvent,
            completion = { success, error ->
                // check if access guranteed
                if (success) {
                    // TODO will check the calender previous events to prevent duplication
                    // prepare the event date
                    val event = EKEvent.eventWithEventStore(eventStore).apply {
                        title = eventTitle
                        startDate = dateFromTimestamp(eventStartTime)
                        endDate = dateFromTimestamp(eventEndTime)
                        notes = eventDescription
                        calendar = eventStore.defaultCalendarForNewEvents
                    }

                    // add the event
                    eventStore.saveEvent(
                        event = event,
                        span = EKSpan.EKSpanThisEvent,
                        error = null
                    )
                    status = CalenderEventStatus.EVENT_ADDED
                } else {
                    status = CalenderEventStatus.EVENT_NOT_ADDED
                }
            })

        return status
    }

    private fun isEventExist(
        eventStore: EKEventStore,
        title: String,
        startTime: Long,
        endTime: Long
    ): Boolean {
        // What about Calendar entries?
        val startDate = dateFromTimestamp(startTime)
        val endDate = dateFromTimestamp(endTime)
        val predicate = eventStore.predicateForEventsWithStartDate(
            startDate, endDate = endDate, calendars = null
        )
        // fetching the whole events list
        val events: List<EKEvent> = eventStore.eventsMatchingPredicate(predicate) as List<EKEvent>

        // check if the event already exists
        return events.contains { it.title.equals(title, ignoreCase = true) }
    }
}