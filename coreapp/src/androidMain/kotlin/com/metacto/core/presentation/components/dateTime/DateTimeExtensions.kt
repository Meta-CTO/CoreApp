package com.metacto.core.presentation.components.dateTime

import java.util.Calendar

actual fun daysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    // Reset the calendar day to the first day of the month to avoid rolling over to the next month
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.YEAR, year)
    return calendar.getActualMaximum(Calendar.DATE)
}
