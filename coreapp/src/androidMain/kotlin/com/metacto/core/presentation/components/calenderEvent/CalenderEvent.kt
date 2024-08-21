package com.metacto.core.presentation.components.calenderEvent

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.utils.extensions.orOne
import java.util.TimeZone


class CalenderEvent(
    private val context: Context,
    private val permissionManager: IPermissionManager
) : ICalenderEvent {

    companion object {
        // The indices for the projection array above.
        private val CALENDER_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.VISIBLE,
        )
        private const val PROJECTION_ID_INDEX = 0
        private const val PROJECTION_VISIBLE_INDEX = 1

    }

    override suspend fun addEventToCalender(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: Long,
        eventEndTime: Long
    ): CalenderEventStatus {

        if (permissionManager.isPermissionGranted(Permission.CALENDER)) {

            // get the active calender id
            val calenderId = getActiveCalender().orOne()
            // add the event to calender
            sendEventToCalender(
                calenderId,
                eventTitle,
                eventDescription,
                eventStartTime,
                eventEndTime
            )
            return CalenderEventStatus.EVENT_ADDED

        } else {

            // if the user not grant the permissions at all
            if (permissionManager.getPermissionState(Permission.CALENDER) == PermissionState.DeniedAlways) {

                // use the intent to navigate the user to calender with the event
                openCalenderEventIntent(eventTitle, eventDescription, eventStartTime, eventEndTime)
                return CalenderEventStatus.EVENT_NOT_ADDED
            } else {
                // request the permission
                permissionManager.requestPermission(Permission.CALENDER)
                return CalenderEventStatus.EVENT_NOT_ADDED
            }
        }
    }

    private fun getActiveCalender(): Long? {
        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val contentResolver = context.contentResolver
        val selection = ""
        val selectionArgs = emptyArray<String>()
        val cur: Cursor? =
            contentResolver.query(uri, CALENDER_PROJECTION, selection, selectionArgs, null)

        var calId: Long? = null
        while (cur?.moveToNext() == true) {
            // Get the field values
            val visible = cur.getInt(PROJECTION_VISIBLE_INDEX)

            // check if the calender is visible or not
            if (visible == 1) {
                // return the calender id
                calId = cur.getLong(PROJECTION_ID_INDEX)
                break
            }
        }
        return calId
    }

    private fun sendEventToCalender(
        calenderId: Long,
        title: String,
        description: String,
        startTime: Long,
        endTime: Long
    ) {
        val contentResolver = context.contentResolver

//     Use the cursor to step through the returned records
        val values = ContentValues().apply {
            // The new display name for the calendar
            put(Events.DTSTART, startTime)
            put(Events.DTEND, endTime)
            put(Events.TITLE, title)
            put(Events.DESCRIPTION, description)
            put(Events.CALENDAR_ID, calenderId)
            put(Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }
        val updateUri: Uri? = contentResolver.insert(Events.CONTENT_URI, values)
    }

    private fun openCalenderEventIntent(
        title: String,
        description: String,
        startTime: Long,
        endTime: Long
    ) {
        val intent = Intent(Intent.ACTION_EDIT).apply {
            setType("vnd.android.cursor.item/event")
            putExtra(Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            putExtra(Events.ALL_DAY, false)// periodicity
            putExtra(Events.DESCRIPTION, description)
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}