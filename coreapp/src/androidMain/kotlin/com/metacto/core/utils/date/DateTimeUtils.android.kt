package com.metacto.core.utils.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinLocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDateTime as JavaLocalDateTime
import java.time.LocalDate as JavaLocalDate
import java.time.LocalTime as JavaLocalTime

actual fun LocalDateTime.format(format: String, language: String): String {
    return try {
        val locale = Locale.forLanguageTag(language)
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        this.toJavaLocalDateTime().format(formatter)
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}

actual fun LocalDate.format(format: String, language: String): String {
    return try {
        val locale = Locale.forLanguageTag(language)
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        this.toJavaLocalDate().format(formatter)
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}

actual fun LocalTime.format(format: String, language: String): String {
    return try {
        val locale = Locale.forLanguageTag(language)
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        this.toJavaLocalTime().format(formatter)
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}

actual fun String.parseLocalDateTime(format: String, language: String): LocalDateTime? {
    return try {
        val locale = Locale.forLanguageTag(language)
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        val javaLdt = JavaLocalDateTime.parse(this, formatter)
        javaLdt.toKotlinLocalDateTime()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

actual fun String.parseLocalDate(format: String, language: String): LocalDate? {
    return try {
        val locale = Locale.forLanguageTag(language)
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        val javaLd = JavaLocalDate.parse(this, formatter)
        javaLd.toKotlinLocalDate()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

actual fun String.parseLocalTime(format: String, language: String): LocalTime? {
    return try {
        val locale = Locale.forLanguageTag(language)
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        val javaLt = JavaLocalTime.parse(this, formatter)
        javaLt.toKotlinLocalTime()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}
