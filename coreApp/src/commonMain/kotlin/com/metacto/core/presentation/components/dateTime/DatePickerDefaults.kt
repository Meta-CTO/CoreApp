package com.metacto.core.presentation.components.dateTime

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@OptIn(ExperimentalMaterial3Api::class)
object DatePickerDefaults {
    val DEFAULT_MIN_DATE = LocalDate(
        dayOfMonth = 1,
        month = Month.JANUARY,
        year = 1900
    )
    val DEFAULT_MAX_DATE = LocalDate(
        dayOfMonth = 31,
        month = Month.DECEMBER,
        year = 2100
    )

    @Composable
    fun colors() = DatePickerDefaults.colors(
        containerColor = CoreTheme.colors.background,
        titleContentColor = CoreTheme.colors.secondary,
        headlineContentColor = CoreTheme.colors.secondary,
        selectedDayContainerColor = CoreTheme.colors.primary,
        selectedDayContentColor = CoreTheme.colors.secondary,
        selectedYearContainerColor = CoreTheme.colors.primary,
        selectedYearContentColor = CoreTheme.colors.secondary,
        currentYearContentColor = CoreTheme.colors.secondary,
        yearContentColor = CoreTheme.colors.secondary,
        dayInSelectionRangeContainerColor = CoreTheme.colors.primary,
        weekdayContentColor = CoreTheme.colors.secondary,
        dayContentColor = CoreTheme.colors.secondary,
        disabledDayContentColor = CoreTheme.colors.secondaryDisabled,
        todayDateBorderColor = CoreTheme.colors.primary,
        todayContentColor = CoreTheme.colors.secondary,
        subheadContentColor = CoreTheme.colors.secondary,
        dayInSelectionRangeContentColor = CoreTheme.colors.secondary,
        disabledSelectedDayContentColor = CoreTheme.colors.background
    )
}