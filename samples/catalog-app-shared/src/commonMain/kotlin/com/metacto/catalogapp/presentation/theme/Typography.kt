package com.metacto.catalogapp.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.metacto.core.ui.extensions.FontFamily
import com.metacto.core.ui.theme.CoreTypography
import com.metacto.catalogapp.resources.Res
import com.metacto.catalogapp.resources.quasimoda_black
import com.metacto.catalogapp.resources.quasimoda_bold
import com.metacto.catalogapp.resources.quasimoda_light
import com.metacto.catalogapp.resources.quasimoda_medium
import com.metacto.catalogapp.resources.quasimoda_regular
import com.metacto.catalogapp.resources.quasimoda_semibold
import com.metacto.catalogapp.resources.quasimoda_thin
import org.jetbrains.compose.resources.Font


// Define font families
@Composable
fun getQuasimodaFontFamily() = FontFamily(
    Font(Res.font.quasimoda_black, FontWeight.Black),
    Font(Res.font.quasimoda_bold, FontWeight.Bold),
    Font(Res.font.quasimoda_semibold, FontWeight.SemiBold),
    Font(Res.font.quasimoda_medium, FontWeight.Medium),
    Font(Res.font.quasimoda_regular, FontWeight.Normal),
    Font(Res.font.quasimoda_light, FontWeight.Light),
    Font(Res.font.quasimoda_thin, FontWeight.Thin)
)

@Immutable
data class Primary(
    val base: TextStyle
) {
    val black = FontSizes(base.copy(fontWeight = FontWeight.Black))
    val extraBold = FontSizes(base.copy(fontWeight = FontWeight.ExtraBold))
    val bold = FontSizes(base.copy(fontWeight = FontWeight.Bold))
    val semiBold = FontSizes(base.copy(fontWeight = FontWeight.SemiBold))
    val medium = FontSizes(base.copy(fontWeight = FontWeight.Medium))
    val regular = FontSizes(base.copy(fontWeight = FontWeight.Normal))
    val light = FontSizes(base.copy(fontWeight = FontWeight.Light))
    val thin = FontSizes(base.copy(fontWeight = FontWeight.Thin))
}

@Immutable
data class FontSizes(
    val base: TextStyle
) {
    val _8 = base.copy(fontSize = 8.sp)
    val _10 = base.copy(fontSize = 10.sp)
    val _12 = base.copy(fontSize = 12.sp)
    val _14 = base.copy(fontSize = 14.sp)
    val _16 = base.copy(fontSize = 16.sp)
    val _18 = base.copy(fontSize = 18.sp)
    val _20 = base.copy(fontSize = 20.sp)
    val _22 = base.copy(fontSize = 22.sp)
    val _24 = base.copy(fontSize = 24.sp)
    val _26 = base.copy(fontSize = 26.sp)
    val _28 = base.copy(fontSize = 28.sp)
    val _32 = base.copy(fontSize = 32.sp)
    val _40 = base.copy(fontSize = 40.sp)
    val _72 = base.copy(fontSize = 72.sp)
}

internal val LocalAppTypography = staticCompositionLocalOf { AppTypography() }

// Create app typography
@Immutable
data class AppTypography(
    val primary: Primary = Primary(TextStyle())
)

// Create core typography
internal fun AppTypography.getCoreTypography(): CoreTypography {
    val coreTypography = CoreTypography()

    return coreTypography.copy(
        primary = primary.base,
        primaryFilledButton = coreTypography.primaryFilledButton.copy(
            textStyle = primary.medium._16
        ),
        tertiaryFilledButton = coreTypography.tertiaryFilledButton.copy(
            textStyle = primary.medium._16
        ),
        primaryStrokedButton = coreTypography.primaryStrokedButton.copy(
            textStyle = primary.medium._16
        ),
        primaryTextButton = coreTypography.primaryTextButton.copy(
            textStyle = primary.medium._16
        ),
        secondaryTextButton = coreTypography.secondaryTextButton.copy(
            textStyle = primary.medium._16
        )
    )
}