package com.metacto.core.utils

import android.icu.text.SimpleDateFormat
import kotlinx.datetime.Instant
import java.util.Locale
import java.util.TimeZone

actual typealias Date = java.util.Date

actual fun Date.toMillis(): Long = this.time

actual object KMMDateFormatter {
    actual fun format(instant: Instant, format: String): String {
        val timestamp = instant.toEpochMilliseconds()
        val date = Date(timestamp)

        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(date)
    }

    @Throws(Throwable::class)
    actual fun stringToDate(
        string: String,
        format: String
    ): Date {
        val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())
        return sdf.parse(string) ?: throw Throwable("Can't parse $string")
    }

    @Throws(Throwable::class)
    actual fun utcStringToDate(
        string: String,
        format: String
    ): Date {
        val sdf = java.text.SimpleDateFormat(format, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        return sdf.parse(string) ?: throw Throwable("Can't convert $string using format ($format)")
    }

    @Throws(Throwable::class)
    actual fun dateToString(
        date: Date,
        format: String
    ): String {
        val sdf = java.text.SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    @Throws(Throwable::class)
    actual fun convertDateFormat(
        date: String,
        fromFormat: String,
        toFormat: String
    ): String {
        val parsedDate = stringToDate(string = date, format = fromFormat)
        return dateToString(date = parsedDate, format = toFormat)
    }

    @Throws(Throwable::class)
    actual fun dateToUTCString(date: Date, format: String): String {
        val sdf = java.text.SimpleDateFormat(format, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        return sdf.format(date)
    }
}