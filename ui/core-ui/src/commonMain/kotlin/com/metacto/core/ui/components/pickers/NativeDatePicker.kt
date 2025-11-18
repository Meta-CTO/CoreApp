package com.metacto.core.ui.components.pickers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import kotlinx.datetime.LocalDate

@Composable
expect fun NativeDatePicker(
    onSnappedDate: (LocalDate) -> Unit,
    minDate: LocalDate,
    maxDate: LocalDate,
    startDate: LocalDate,
    size: DpSize,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent
)
