package com.metacto.core.presentation.globalState.models

import kotlinx.datetime.LocalDate

data class DatePickerParams(
    val selectedDate: LocalDate? = null,
    val minDate: LocalDate? = null,
    val maxDate: LocalDate? = null,
    val isCancellable: Boolean = true,
    val onDatePicked: (LocalDate) -> Unit
)