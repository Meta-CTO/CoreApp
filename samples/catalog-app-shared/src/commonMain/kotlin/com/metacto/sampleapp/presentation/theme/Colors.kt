package com.metacto.sampleapp.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
internal data class AppColors(
    val transparent: Color = Color(0x00FFFFFF),

    val white: Color = Color(0xFFFFFFFF),
    val white10: Color = white.copy(alpha = 0.1f),
    val white20: Color = white.copy(alpha = 0.2f),
    val white30: Color = white.copy(alpha = 0.3f),
    val white40: Color = white.copy(alpha = 0.4f),
    val white50: Color = white.copy(alpha = 0.5f),
    val white60: Color = white.copy(alpha = 0.6f),
    val white70: Color = white.copy(alpha = 0.7f),
    val white80: Color = white.copy(alpha = 0.8f),
    val white90: Color = white.copy(alpha = 0.9f),

    val black: Color = Color(0xFF000000),
    val black10: Color = black.copy(alpha = 0.1f),
    val black20: Color = black.copy(alpha = 0.2f),
    val black30: Color = black.copy(alpha = 0.3f),
    val black40: Color = black.copy(alpha = 0.4f),
    val black50: Color = black.copy(alpha = 0.5f),
    val black60: Color = black.copy(alpha = 0.6f),
    val black70: Color = black.copy(alpha = 0.7f),
    val black80: Color = black.copy(alpha = 0.8f),
    val black90: Color = black.copy(alpha = 0.9f),

    val gray: Color = Color(0xFF909090),
    val darkGray: Color = Color(0xFF1F1F1F),

    val nectarine: Color = Color(0xFFF1C3A9),
    val nectarineDark: Color = Color(0xFFED9E72),
    val miniPeach: Color = Color(0xFFFAF4F0),
    val terra: Color = Color(0xFFC55415),
    val midnight: Color = Color(0xFF271E41),
    val lavender: Color = Color(0xFFD5D8FC),
    val lilac: Color = Color(0xFFF5F6FF),
    val background: Color = miniPeach,
    val coalLight: Color = Color(0xFFC8C8C8),
)

internal val LocalAppColors = staticCompositionLocalOf { AppColors() }
