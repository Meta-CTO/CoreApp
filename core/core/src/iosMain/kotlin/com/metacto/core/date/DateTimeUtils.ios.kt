package com.metacto.core.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierGregorian
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitNanosecond
import platform.Foundation.NSCalendarUnitWeekOfMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateComponentsFormatter
import platform.Foundation.NSDateComponentsFormatterUnitsStyleAbbreviated
import platform.Foundation.NSDateComponentsFormatterZeroFormattingBehaviorDropAll
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDayCalendarUnit
import platform.Foundation.NSHourCalendarUnit
import platform.Foundation.NSLocale
import platform.Foundation.NSMinuteCalendarUnit
import platform.Foundation.NSMonthCalendarUnit
import platform.Foundation.NSSecondCalendarUnit
import platform.Foundation.NSYearCalendarUnit
import platform.Foundation.timeIntervalSince1970

private val timeFormatter = NSDateComponentsFormatter().apply {
    unitsStyle = NSDateComponentsFormatterUnitsStyleAbbreviated
    zeroFormattingBehavior = NSDateComponentsFormatterZeroFormattingBehaviorDropAll
    maximumUnitCount = 1
    allowedUnits = (
            NSCalendarUnitYear
                    or NSCalendarUnitMonth
                    or NSCalendarUnitWeekOfMonth
                    or NSCalendarUnitDay
                    or NSCalendarUnitHour
                    or NSCalendarUnitMinute
            )
    includesApproximationPhrase = false
}

actual fun LocalDateTime.format(format: String, langCode: String): String {
    return try {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale(localeIdentifier = langCode)
        }
        formatter.stringFromDate(this.toNSDate())
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}

actual fun LocalDate.format(format: String, langCode: String): String {
    return try {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale(localeIdentifier = langCode)
        }
        formatter.stringFromDate(this.toNSDate())
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}

actual fun LocalTime.format(format: String, langCode: String): String {
    return try {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale(localeIdentifier = langCode)
        }
        formatter.stringFromDate(this.toNSDate())
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}

actual fun String.parseLocalDateTime(format: String, langCode: String): LocalDateTime? {
    return try {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale(localeIdentifier = langCode)
        }
        return formatter.dateFromString(this)?.toKotlinLocalDateTime()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

actual fun String.parseLocalDate(format: String, langCode: String): LocalDate? {
    return try {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale(localeIdentifier = langCode)
        }
        return formatter.dateFromString(this)?.toKotlinLocalDate()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

actual fun String.parseLocalTime(format: String, langCode: String): LocalTime? {
    return try {
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            locale = NSLocale(localeIdentifier = langCode)
        }
        return formatter.dateFromString(this)?.toKotlinLocalTime()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

private fun LocalDateTime.toNSDate(): NSDate {
    val components = NSDateComponents().apply {
        year = this@toNSDate.year.toLong()
        month = this@toNSDate.monthNumber.toLong()
        day = this@toNSDate.dayOfMonth.toLong()
        hour = this@toNSDate.hour.toLong()
        minute = this@toNSDate.minute.toLong()
        second = this@toNSDate.second.toLong()
    }
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierGregorian)!!
    return calendar.dateFromComponents(components)!!
}

private fun LocalDate.toNSDate(): NSDate {
    val components = NSDateComponents().apply {
        year = this@toNSDate.year.toLong()
        month = this@toNSDate.monthNumber.toLong()
        day = this@toNSDate.dayOfMonth.toLong()
    }
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierGregorian)!!
    return calendar.dateFromComponents(components)!!
}

private fun LocalTime.toNSDate(): NSDate {
    val components = NSDateComponents().apply {
        year = 1970L
        month = 1L
        day = 1L
        hour = this@toNSDate.hour.toLong()
        minute = this@toNSDate.minute.toLong()
        second = this@toNSDate.second.toLong()
    }
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierGregorian)!!
    return calendar.dateFromComponents(components)!!
}

private fun NSDate.toKotlinLocalDateTime(): LocalDateTime {
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierGregorian)!!
    val components = calendar.components(
        NSYearCalendarUnit or NSMonthCalendarUnit or
                NSDayCalendarUnit or NSHourCalendarUnit or
                NSMinuteCalendarUnit or NSSecondCalendarUnit or
                NSCalendarUnitNanosecond,
        fromDate = this
    )
    return LocalDateTime(
        year = components.year.toInt(),
        monthNumber = components.month.toInt(),
        dayOfMonth = components.day.toInt(),
        hour = components.hour.toInt(),
        minute = components.minute.toInt(),
        second = components.second.toInt(),
        nanosecond = components.nanosecond.toInt()
    )
}

private fun NSDate.toKotlinLocalDate(): LocalDate {
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierGregorian)!!
    val components = calendar.components(
        NSYearCalendarUnit or NSMonthCalendarUnit or NSDayCalendarUnit,
        fromDate = this
    )
    return LocalDate(
        year = components.year.toInt(),
        monthNumber = components.month.toInt(),
        dayOfMonth = components.day.toInt()
    )
}

private fun NSDate.toKotlinLocalTime(): LocalTime {
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierGregorian)!!
    val components = calendar.components(
        NSHourCalendarUnit or NSMinuteCalendarUnit or NSSecondCalendarUnit or NSCalendarUnitNanosecond,
        fromDate = this
    )
    return LocalTime(
        hour = components.hour.toInt(),
        minute = components.minute.toInt(),
        second = components.second.toInt(),
        nanosecond = components.nanosecond.toInt()
    )
}

actual fun LocalDate.formatToRelativeDate(): String {
    return this.toMillis().timestampToReadableDate()
}

actual fun LocalDateTime.formatToRelativeDate(): String {
    return this.toMillis().timestampToReadableDate()
}

private fun Long.timestampToReadableDate(): String {
    // Prepare timestamp seconds
    val timestampSeconds = this.div(1000).toDouble()

    // Create dates
    val now = NSDate()
    val date = this.toLocalDateTime().toNSDate()

    // Check and return
    return when {
        (now.timeIntervalSince1970 - timestampSeconds) <= 59 -> {
            // Less than one minute
            "Just Now"
        }

        else -> {
            // Or convert to readable date
            timeFormatter.stringFromDate(startDate = date, toDate = now).orEmpty()
        }
    }
}

// Convert LocalDateTime to NSDateComponents
fun LocalDateTime.toNSDateComponent(): NSDateComponents {
    val components = NSDateComponents()
    components.year = year.toLong()
    components.month = monthNumber.toLong()
    components.day = this.dayOfMonth.toLong()
    components.hour = this.hour.toLong()
    components.minute = this.minute.toLong()
    components.second = this.second.toLong()
    components.calendar = NSCalendar.currentCalendar()
    return components
}