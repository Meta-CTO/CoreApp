package com.metacto.core.presentation.components.dateTime

import kotlinx.datetime.LocalTime

object TimePickerDefaults {
    val DEFAULT_MIN_TIME = LocalTime(
        hour = 0,
        minute = 0,
        second = 0
    )
    val DEFAULT_MAX_TIME = LocalTime(
        hour = 23,
        minute = 59,
        second = 59
    )
}