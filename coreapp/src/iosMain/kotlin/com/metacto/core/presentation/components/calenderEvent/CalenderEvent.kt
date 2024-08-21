package com.metacto.core.presentation.components.calenderEvent

import com.metacto.core.utils.dateFromTimestamp
import com.metacto.core.utils.extensions.contains
import com.metacto.strapikmm.util.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import platform.EventKit.EKEntityType
import platform.EventKit.EKEvent
import platform.EventKit.EKEventStore
import platform.EventKit.EKSpan


class CalenderEvent : ICalenderEvent {

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
                    // check if the event already exists
                    if (checkEventExists(
                            eventStore,
                            eventTitle,
                            eventStartTime,
                            eventStartTime
                        ).not()
                    ) {
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
                            event = event, span = EKSpan.EKSpanThisEvent, error = null
                        )
                        status = CalenderEventStatus.EVENT_ADDED
                    } else {
                        status = CalenderEventStatus.EVENT_ALREADY_ADDED
                    }
                } else {
                    status = CalenderEventStatus.EVENT_NOT_ADDED
                }
            })

        return status
    }

    private fun checkEventExists(
        eventStore: EKEventStore, title: String, startTime: Long, endTime: Long
    ): Boolean {

        // What about Calendar entries?
        val startDate = dateFromTimestamp(startTime)
        val endDate = dateFromTimestamp(endTime)
        val predicate = eventStore.predicateForEventsWithStartDate(
            startDate, endDate = endDate, calendars = null
        )

        val events: List<EKEvent> = eventStore.eventsMatchingPredicate(predicate) as List<EKEvent>

        Logger("CalenderEvent").log("events ${events.size}")

        events.forEach {
            Logger("CalenderEvent").log("${it.startDate}  ${it.title}")
        }
        // check if the event already exists
        return events.contains { it.title.equals(title, ignoreCase = true) }
    }
}