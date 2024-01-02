package com.metacto.core.utils

import kotlinx.datetime.Instant
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierISO8601
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localTimeZone
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZoneWithAbbreviation


actual typealias Date = NSDate

actual fun Date.toMillis(): Long {
    return (timeIntervalSince1970 * 1000).toLong()
}

actual object KMMDateFormatter {
    actual fun format(instant: Instant, format: String): String {
        return formatInstantToiOSDateTime(instant.toEpochMilliseconds(), format)
    }

    private fun formatInstantToiOSDateTime(instant: Long, format: String): String {
        val timestamp = instant / 1000.0 // Convert from milliseconds to seconds
        val date = NSDate.dateWithTimeIntervalSince1970(timestamp)

        val dateFormatter = NSDateFormatter().apply {
            setLocale(NSLocale(localeIdentifier = "en_US"))
            setDateFormat(format)
        }

        return dateFormatter.stringFromDate(date)
    }

    private val dateFormatter = NSDateFormatter().apply {
        locale = NSLocale.currentLocale
        lenient = true
        calendar = NSCalendar(calendarIdentifier = NSCalendarIdentifierISO8601)
    }

    private val utcTimeZone: NSTimeZone
        get() = NSTimeZone.timeZoneWithAbbreviation("UTC") ?: throw Throwable("Unable to construct UTC TimeZone")

    private val localTimeZone: NSTimeZone
        get() = NSTimeZone.localTimeZone

    @Throws(Throwable::class)
    actual fun stringToDate(
        string: String,
        format: String
    ): Date {
        dateFormatter.dateFormat = format
        dateFormatter.timeZone = localTimeZone
        return dateFormatter.dateFromString(string) ?: NSDate()
    }

    @Throws(Throwable::class)
    actual fun dateToString(
        date: Date,
        format: String
    ): String {
        dateFormatter.dateFormat = format
        dateFormatter.timeZone = localTimeZone
        return dateFormatter.stringFromDate(date)
    }

    @Throws(Throwable::class)
    actual fun convertDateFormat(
        date: String,
        fromFormat: String,
        toFormat: String
    ): String {
        val formattedDate = stringToDate(date, fromFormat)
        return dateToString(formattedDate, toFormat)
    }

    @Throws(Throwable::class)
    actual fun dateToUTCString(
        date: Date,
        format: String
    ): String {
        dateFormatter.dateFormat = format
        dateFormatter.timeZone = utcTimeZone
        return dateFormatter.stringFromDate(date)
    }

    @Throws(Throwable::class)
    actual fun utcStringToDate(
        string: String,
        format: String
    ): Date {
        dateFormatter.dateFormat = format
        dateFormatter.timeZone = utcTimeZone

        val date = dateFormatter.dateFromString(string) ?: throw Throwable("Unable to convert $string to date with format $format")
        val dateString = dateToString(date, format)

        dateFormatter.timeZone = localTimeZone
        return dateFormatter.dateFromString(dateString) ?: throw Throwable("Unable to convert $dateString to date with $format")
    }

}