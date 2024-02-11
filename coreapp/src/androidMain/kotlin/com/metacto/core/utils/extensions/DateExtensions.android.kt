package com.metacto.core.utils.extensions

import android.text.format.DateUtils
import java.util.Calendar
import java.util.Date

actual fun daysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance().apply {
        // Reset the calendar day to the first day of the month to avoid rolling over to the next month
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
    }

    return calendar.getActualMaximum(Calendar.DATE)
}

actual fun Long.toReadableDate(): String {
    val now = Date().time
    val difference = now - this

    return when {
        difference < DateUtils.MINUTE_IN_MILLIS -> "Just now"
        else -> DateUtils
            .getRelativeTimeSpanString(this, now, DateUtils.MINUTE_IN_MILLIS)
            .toString()
    }
}