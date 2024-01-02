package com.sampleApp.app.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreColors
import com.metacto.core.presentation.theme.CoreShapes
import com.metacto.core.presentation.theme.CoreSpacings
import com.metacto.core.presentation.theme.CoreTypography
import com.metacto.core.presentation.theme.LocalCoreColors
import com.metacto.core.presentation.theme.LocalCoreShapes
import com.metacto.core.presentation.theme.LocalCoreSpacings
import com.metacto.core.presentation.theme.LocalCoreTypography

internal object AppTheme {
    val colors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val spacings
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacings.current

    val shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}

@Composable
private fun ProvideTheme(
    content: @Composable () -> Unit
) {
    // Provide different theme implementations here if you need

    // Get font families
    val fenwickFontFamily = getFenwickFontFamily()
    val quasimodaFontFamily = getQuasimodaFontFamily()

    // Create app & core typographies
    val appTypography = remember(fenwickFontFamily, quasimodaFontFamily) {
        AppTypography(
            fenwick = TextStyle(fontFamily = fenwickFontFamily),
            quasimoda = TextStyle(fontFamily = quasimodaFontFamily),
        )
    }
    val coreTypography = remember(fenwickFontFamily, quasimodaFontFamily) {
        CoreTypography(
            primary = TextStyle(fontFamily = quasimodaFontFamily)
        )
    }

    // Create other app & core theme sets
    val appColors = remember { AppColors() }
    val appShapes = remember { AppShapes() }
    val appSpacings = remember { AppSpacings() }
    val coreColors = remember { CoreColors() }
    val coreShapes = remember { CoreShapes() }
    val coreSpacings = remember { CoreSpacings() }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppShapes provides appShapes,
        LocalAppSpacings provides appSpacings,
        LocalCoreColors provides coreColors,
        LocalCoreShapes provides coreShapes,
        LocalCoreSpacings provides coreSpacings,
        LocalAppTypography provides appTypography,
        LocalCoreTypography provides coreTypography
    ) {
        content()
    }
}

@Composable
internal fun AppThemeContent(
    content: @Composable () -> Unit
) {
    // Create material color scheme
    val materialColorScheme = lightColorScheme(
        primary = AppTheme.colors.nectarine,
        onPrimary = AppTheme.colors.midnight,
        primaryContainer = AppTheme.colors.miniPeach,
        onPrimaryContainer = AppTheme.colors.midnight,
        secondary = AppTheme.colors.midnight,
        onSecondary = AppTheme.colors.white,
        secondaryContainer = AppTheme.colors.lavender,
        onSecondaryContainer = AppTheme.colors.midnight,
        tertiary = AppTheme.colors.gray,
        onTertiary = AppTheme.colors.white,
        tertiaryContainer = AppTheme.colors.white,
        onTertiaryContainer = AppTheme.colors.midnight,
        background = AppTheme.colors.background,
        surface = AppTheme.colors.background
    )

    // Provide different theme then render material theme
    ProvideTheme {
        MaterialTheme(
            colorScheme = materialColorScheme
        ) {
            Scaffold(
                containerColor = AppTheme.colors.background,
                modifier = Modifier.background(AppTheme.colors.background)
            ) {
                content()
            }
        }
    }
}
