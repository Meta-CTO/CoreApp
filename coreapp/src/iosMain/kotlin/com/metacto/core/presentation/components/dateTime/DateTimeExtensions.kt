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
    components.year = year.convert()
    components.month = month.convert()

    val date = calendar.dateFromComponents(components) ?: return 0
    val range = calendar.rangeOfUnit(NSCalendarUnitDay, NSCalendarUnitMonth, date)
    return range.size
}
