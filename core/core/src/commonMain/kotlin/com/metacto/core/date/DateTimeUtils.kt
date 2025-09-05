package com.metacto.core.date

import com.metacto.core.extensions.getSystemLanguage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Long.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
}

fun Long.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return this.toLocalDateTime(timeZone).date
}

fun Long.toLocalTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return this.toLocalDateTime(timeZone).time
}

fun LocalDate.toMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.atStartOfDayIn(timeZone).toEpochMilliseconds()
}

fun LocalDateTime.toMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.toInstant(timeZone).toEpochMilliseconds()
}

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return LocalDateTime.now(timeZone).date
}

fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return LocalDateTime.now(timeZone).time
}

fun LocalDate.isPast(): Boolean {
    return this < LocalDate.now()
}

fun CoreDateTime.toMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.value.toMillis(timeZone)
}

fun CoreDateTime.format(
    format: String,
    langCode: String = getSystemLanguage().code
): String {
    return this.value.format(format, langCode)
}

fun CoreDateTime.formatToRelativeDate(): String {
    return this.value.formatToRelativeDate()
}

fun CoreDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): CoreDateTime {
    return CoreDateTime(LocalDateTime.now(timeZone))
}

fun Long.toCoreDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): CoreDateTime {
    return CoreDateTime(this.toLocalDateTime(timeZone))
}

fun CoreDate.toMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.value.toMillis(timeZone)
}

fun CoreDate.format(
    format: String,
    langCode: String = getSystemLanguage().code
): String {
    return this.value.format(format, langCode)
}

fun CoreDate.formatToRelativeDate(): String {
    return this.value.formatToRelativeDate()
}

fun CoreDate.isPast(): Boolean {
    return this.value.isPast()
}

fun CoreDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): CoreDate {
    return CoreDate(LocalDate.now(timeZone))
}

fun Long.toCoreDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): CoreDate {
    return CoreDate(this.toLocalDate(timeZone))
}

fun CoreTime.format(
    format: String,
    langCode: String = getSystemLanguage().code
): String {
    return this.value.format(format, langCode)
}

fun CoreTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): CoreTime {
    return CoreTime(LocalTime.now(timeZone))
}

fun Long.toCoreTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): CoreTime {
    return CoreTime(this.toLocalTime(timeZone))
}

expect fun LocalDateTime.format(
    format: String,
    langCode: String = getSystemLanguage().code
): String

expect fun LocalDate.format(
    format: String,
    langCode: String = getSystemLanguage().code
): String

expect fun LocalTime.format(
    format: String,
    langCode: String = getSystemLanguage().code
): String

expect fun String.parseLocalDateTime(
    format: String,
    langCode: String = getSystemLanguage().code
): LocalDateTime?

expect fun String.parseLocalDate(
    format: String,
    langCode: String = getSystemLanguage().code
): LocalDate?

expect fun String.parseLocalTime(
    format: String,
    langCode: String = getSystemLanguage().code
): LocalTime?

expect fun LocalDate.formatToRelativeDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): String

expect fun LocalDateTime.formatToRelativeDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): String