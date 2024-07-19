package com.metacto.core.presentation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class CoreShapes(
    val xSmall: RoundedCornerShape = RoundedCornerShape(8.dp),
    val small: RoundedCornerShape = RoundedCornerShape(10.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(14.dp),
    val xLarge: RoundedCornerShape = RoundedCornerShape(16.dp),
    val xxLarge: RoundedCornerShape = RoundedCornerShape(20.dp),
    val xxxLarge: RoundedCornerShape = RoundedCornerShape(24.dp),
    val circle: RoundedCornerShape = CircleShape,
    val wheelPickerItem: RoundedCornerShape = RoundedCornerShape(16.dp),
    val itemPickerItem: RoundedCornerShape = RoundedCornerShape(0),
    val sheet: RoundedCornerShape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp
    )
)

val LocalCoreShapes = staticCompositionLocalOf { CoreShapes() }