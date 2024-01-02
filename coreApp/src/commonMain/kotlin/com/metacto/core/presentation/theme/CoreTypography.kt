package com.metacto.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Define core typography
@Immutable
data class CoreTypography(
    val primary: TextStyle = TextStyle(),
    val primaryBold: TextStyle = primary.copy(
        fontWeight = FontWeight.Bold
    ),
    val primarySemiBold: TextStyle = primary.copy(
        fontWeight = FontWeight.SemiBold
    ),
    val primaryMedium: TextStyle = primary.copy(
        fontWeight = FontWeight.Medium
    ),
    val primaryRegular: TextStyle = primary.copy(
        fontWeight = FontWeight.Normal
    ),
    val primaryLight: TextStyle = primary.copy(
        fontWeight = FontWeight.Light
    ),

    val headline: TextStyle = primaryBold.copy(
        fontSize = 24.sp
    ),
    val sheetTitle: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),

    val btnLabelMedium: TextStyle = primarySemiBold.copy(
        fontSize = 20.sp
    ),
    val btnLabelSmall: TextStyle = primarySemiBold.copy(
        fontSize = 16.sp
    ),

    val labelMedium: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),
    val labelSmall: TextStyle = primaryMedium.copy(
        fontSize = 14.sp
    ),

    val bodyXXLarge: TextStyle = primaryMedium.copy(
        fontSize = 24.sp
    ),
    val bodyXLarge: TextStyle = primaryMedium.copy(
        fontSize = 20.sp
    ),
    val bodyLarge: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),
    val bodyMedium: TextStyle = primaryMedium.copy(
        fontSize = 14.sp
    ),
    val bodySmall: TextStyle = primaryMedium.copy(
        fontSize = 12.sp
    ),
    val snackBarMsg: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),
    val inputFieldText: TextStyle = primaryMedium.copy(
        fontSize = 18.sp
    ),
    val numberSelector: TextStyle = primaryBold.copy(
        fontSize = 20.sp
    ),
)

val LocalCoreTypography = staticCompositionLocalOf { CoreTypography() }