package com.metacto.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CoreColors(
    val transparent: Color = Color.Transparent,
    val black: Color = Color(0xFF000000),
    val white: Color = Color(0xFFFFFFFF),
    val gray: Color = Color(0xFF6F6F6F),
    val lightGray: Color = Color(0xFFB7B7B7),
    val lightGray20: Color = lightGray.copy(alpha = 0.2f),
    val lightGray50: Color = Color(0xFFEEEEEE),
    val darkGreen: Color = Color(0xFF00863A),
    val lightGreen: Color = Color(0xFFECFDF3),
    val red: Color = Color(0xFFD92D20),
    val lightRed: Color = Color(0xFFFEF3F2),
    val nectarine: Color = Color(0xFFF1C3A9),
    val nectarineDark: Color = Color(0xFFF1AA82),
    val miniPeach: Color = Color(0xFFFAF4F0),
    val midnight: Color = Color(0xFF271E41),
    val lavender: Color = Color(0xFFD5D8FC),
    val terra: Color = Color(0xFFC55415),

    val primary: Color = nectarine,
    val primaryDark: Color = nectarineDark,
    val primaryStrongDark: Color = terra,
    val onPrimary: Color = midnight,
    val onStrongDarkPrimary: Color = white,
    val primaryContainer: Color = miniPeach,
    val onPrimaryContainer: Color = midnight,

    val secondary: Color = midnight,
    val secondaryDisabled: Color = secondary.copy(alpha = 0.5f),
    val onSecondary: Color = white,
    val secondaryContainer: Color = lavender,
    val onSecondaryContainer: Color = midnight,

    val tertiary: Color = gray,
    val onTertiary: Color = white,
    val tertiaryBorder: Color = lightGray50,

    val danger: Color = red,
    val dangerContainer: Color = lightRed,

    val success: Color = darkGreen,
    val successContainer: Color = lightGreen,

    val sheetPrimary: Color = midnight,
    val sheetBackground: Color = white,

    val background: Color = miniPeach,
    val outline: Color = lightGray20,

    val placeholder: Color = Color(0xFF8F8F8F),
    val optionsArrow: Color = black,
    val divider: Color = Color(0xFFA5A5A5),
)

val LocalCoreColors = staticCompositionLocalOf { CoreColors() }