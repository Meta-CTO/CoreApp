package com.metacto.core.utils

import kotlinx.datetime.Instant

expect class Date()

expect fun Date.toMillis(): Long

expect object KMMDateFormatter {
    fun format(instant: Instant, format: String): String
    @Throws(Throwable::class)
    fun stringToDate(string: String, format: String): Date
    @Throws(Throwable::class)
    fun utcStringToDate(string: String, format: String): Date
    @Throws(Throwable::class)
    fun dateToString(date: Date, format: String): String
    @Throws(Throwable::class)
    fun convertDateFormat(date: String, fromFormat: String, toFormat: String): String
    @Throws(Throwable::class)
    fun dateToUTCString(date: Date, format: String): String
}