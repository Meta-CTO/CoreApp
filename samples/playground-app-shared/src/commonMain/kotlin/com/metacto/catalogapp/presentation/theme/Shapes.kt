package com.metacto.catalogapp.presentation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
internal data class AppShapes(
    val roundedCorner2: RoundedCornerShape = RoundedCornerShape(2.dp),
    val roundedCorner4: RoundedCornerShape = RoundedCornerShape(4.dp),
    val roundedCorner6: RoundedCornerShape = RoundedCornerShape(6.dp),
    val roundedCorner8: RoundedCornerShape = RoundedCornerShape(8.dp),
    val roundedCorner10: RoundedCornerShape = RoundedCornerShape(10.dp),
    val roundedCorner12: RoundedCornerShape = RoundedCornerShape(12.dp),
    val roundedCorner14: RoundedCornerShape = RoundedCornerShape(14.dp),
    val roundedCorner16: RoundedCornerShape = RoundedCornerShape(16.dp),
    val roundedCorner20: RoundedCornerShape = RoundedCornerShape(20.dp),
    val roundedCorner24: RoundedCornerShape = RoundedCornerShape(24.dp),
    val roundedCorner30: RoundedCornerShape = RoundedCornerShape(30.dp),
    val roundedCorner50: RoundedCornerShape = RoundedCornerShape(50.dp),
    val roundedCornerMax: RoundedCornerShape = RoundedCornerShape(Int.MAX_VALUE.dp),
    val circle: RoundedCornerShape = CircleShape
)

internal val LocalAppShapes = staticCompositionLocalOf { AppShapes() }