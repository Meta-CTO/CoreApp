package com.metacto.playground.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import com.metacto.core.language.ILanguageManager
import com.metacto.core.ui.theme.CoreColors
import com.metacto.core.ui.theme.CoreShapes
import com.metacto.core.ui.theme.CoreSpacings
import com.metacto.core.ui.theme.LocalCoreColors
import com.metacto.core.ui.theme.LocalCoreShapes
import com.metacto.core.ui.theme.LocalCoreSpacings
import com.metacto.core.ui.theme.LocalCoreTypography
import org.koin.compose.koinInject

internal val colors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

internal val typography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current

internal val spacings
    @Composable
    @ReadOnlyComposable
    get() = LocalAppSpacings.current

internal val shapes
    @Composable
    @ReadOnlyComposable
    get() = LocalAppShapes.current

@Composable
private fun ProvideTheme(
    content: @Composable () -> Unit
) {
    // Prepare language and layout direction stuff
    val languageManager = koinInject<ILanguageManager>()
    val currentLanguage by remember { languageManager.currentLanguageAsState() }
    val layoutDirection = remember(currentLanguage) {
        if (currentLanguage?.isRtl == true) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    // Get font families
    val quasimodaFontFamily = getQuasimodaFontFamily()

    // Create typographies
    val appTypography = remember(quasimodaFontFamily) {
        AppTypography(
            primary = Primary(TextStyle(fontFamily = quasimodaFontFamily))
        )
    }
    val coreTypography = remember(appTypography) {
        appTypography.getCoreTypography()
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
        LocalCoreTypography provides coreTypography,
        LocalLayoutDirection provides layoutDirection
    ) {
        key(layoutDirection, currentLanguage) {
            content()
        }
    }
}

@Composable
internal fun AppThemeContent(
    content: @Composable () -> Unit
) {
    // Create material color scheme
    val materialColorScheme = lightColorScheme(
        primary = colors.nectarine,
        onPrimary = colors.midnight,
        primaryContainer = colors.miniPeach,
        onPrimaryContainer = colors.midnight,
        secondary = colors.midnight,
        onSecondary = colors.white,
        secondaryContainer = colors.lavender,
        onSecondaryContainer = colors.midnight,
        tertiary = colors.gray,
        onTertiary = colors.white,
        tertiaryContainer = colors.white,
        onTertiaryContainer = colors.midnight,
        background = colors.background,
        surface = colors.background
    )

    // Provide different theme then render material theme
    ProvideTheme {
        MaterialTheme(
            colorScheme = materialColorScheme
        ) {
            Scaffold(
                containerColor = colors.background,
                modifier = Modifier.background(colors.background)
            ) {
                content()
            }
        }
    }
}
