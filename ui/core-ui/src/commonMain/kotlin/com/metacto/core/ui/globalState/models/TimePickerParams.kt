package com.metacto.core.ui.globalState.models

import kotlinx.datetime.LocalTime

data class TimePickerParams(
    val selectedTime: LocalTime? = null,
    val minTime: LocalTime? = null,
    val maxTime: LocalTime? = null,
    val isCancellable: Boolean = true,
    val onTimePicked: (LocalTime) -> Unit
)