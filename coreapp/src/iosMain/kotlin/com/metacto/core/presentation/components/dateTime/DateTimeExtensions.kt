package com.metacto.core.presentation.components.dateTime

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSDateComponents

@OptIn(ExperimentalForeignApi::class)
actual fun daysInMonth(year: Int, month: Int): Int {
    val calendar = NSCalendar.currentCalendar
    val components = NSDateComponents()
    // Reset the calendar day to the first day of the month to avoid rolling over to the next month
    components.day = 1.convert()
    components.month = month.convert()
    components.year = year.convert()

    val date = calendar.dateFromComponents(components) ?: return 0
    val range = calendar.rangeOfUnit(NSCalendarUnitDay, NSCalendarUnitMonth, date)
    return range.size
}
