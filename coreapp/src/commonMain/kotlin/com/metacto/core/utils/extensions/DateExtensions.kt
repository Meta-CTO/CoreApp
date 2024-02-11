package com.metacto.core.utils.extensions

import com.metacto.core.domain.CoreConstants
import com.metacto.core.utils.KMMDateFormatter
import com.metacto.core.utils.toMillis
import com.swensonhe.strapikmm.util.DatetimeUtil
import com.swensonhe.strapikmm.util.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun getCurrentDate(): LocalDate {
    return DatetimeUtil.now().toLocalDate()
}

fun getCurrentTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return Clock.System.now().toLocalDateTime(timeZone).time
}

fun LocalTime.get12FormatHour(): Int {
    val validHour = hour.mod(12)
    return if (validHour == 0) 12 else validHour
}

fun LocalTime.isAM(): Boolean {
    return hour in 0 until 12
}

fun LocalTime.isPM(): Boolean {
    return hour in 12 until 24
}

fun LocalTime.to12FormatString(): String {
    val hour = (hour % 12).toString().padStart(2, '0')
    val period = if (this.hour < 12) "AM" else "PM"
    val minute = minute.toString().padStart(2, '0')
    return "$hour:$minute $period"
}

fun LocalDate.mergeWith(localTime: LocalTime): LocalDateTime {
    return atTime(localTime)
}

fun LocalDateTime.toInstant(): Instant {
    return this.toInstant(TimeZone.currentSystemDefault())
}

fun LocalDate.toLocalDateTime(): LocalDateTime {
    return LocalDateTime(
        date = this,
        time = LocalTime(0, 0)
    )
}

fun LocalDate.toInstant(): Instant {
    return this.toLocalDateTime().toInstant()
}

fun LocalDate.toEpochMilliseconds(): Long {
    return this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
}

fun Long.toLocalDate(): LocalDate {
    return this.toLocalDate().toLocalDate()
}

fun nowInstant(): Instant {
    return DatetimeUtil.now().toInstant(TimeZone.currentSystemDefault())
}

fun LocalDate.toFormattedDate(format: String): String {
    return KMMDateFormatter.format(
        instant = this.toInstant(),
        format = format
    )
}

fun LocalDate.toServerDate(): String {
    return this.toFormattedDate(CoreConstants.SERVER_DATE_FORMAT)
}


fun String.parseServerDate(): LocalDate? {
    return try {
        KMMDateFormatter.stringToDate(
            string = this,
            format = CoreConstants.SERVER_DATE_FORMAT
        ).toMillis().toLocalDate().date
    } catch (_: Throwable) {
        null
    }
}

fun String.parseDate(format: String): LocalDate? {
    return try {
        KMMDateFormatter.stringToDate(
            string = this,
            format = format
        ).toMillis().toLocalDate().date
    } catch (_: Throwable) {
        null
    }
}

expect fun daysInMonth(year: Int, month: Int): Int

expect fun Long.toReadableDate(): String