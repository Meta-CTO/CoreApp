package com.metacto.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.metacto.core.presentation.theme.LocalCoreTypography

object CoreTheme {
    val spacings
        @Composable
        @ReadOnlyComposable
        get() = LocalCoreSpacings.current

    val colors
        @Composable
        @ReadOnlyComposable
        get() = LocalCoreColors.current

    val typography
        @ReadOnlyComposable
        @Composable
        get() = LocalCoreTypography.current

    val shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalCoreShapes.current
}