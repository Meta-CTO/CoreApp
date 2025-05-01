package com.metacto.sampleapp.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.metacto.core.ui.extensions.FontFamily
import com.metacto.sampleapp.resources.Res
import com.metacto.sampleapp.resources.fenwick_bold
import com.metacto.sampleapp.resources.fenwick_light
import com.metacto.sampleapp.resources.fenwick_regular
import com.metacto.sampleapp.resources.quasimoda_black
import com.metacto.sampleapp.resources.quasimoda_bold
import com.metacto.sampleapp.resources.quasimoda_light
import com.metacto.sampleapp.resources.quasimoda_medium
import com.metacto.sampleapp.resources.quasimoda_regular
import com.metacto.sampleapp.resources.quasimoda_semibold
import com.metacto.sampleapp.resources.quasimoda_thin
import org.jetbrains.compose.resources.Font


// Define font families
@Composable
fun getFenwickFontFamily() = FontFamily(
    Font(Res.font.fenwick_bold, FontWeight.Bold),
    Font(Res.font.fenwick_regular, FontWeight.Normal),
    Font(Res.font.fenwick_light, FontWeight.Light)
)

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

// Create app typography
@Immutable
data class AppTypography(
    val fenwick: TextStyle = TextStyle(),
    val quasimoda: TextStyle = TextStyle(),
    val fenwickBold: TextStyle = fenwick.copy(
        fontWeight = FontWeight.Bold
    ),
    val fenwickRegular: TextStyle = fenwick.copy(
        fontWeight = FontWeight.Normal
    ),
    val fenwickLight: TextStyle = fenwick.copy(
        fontWeight = FontWeight.Light
    ),
    val quasimodaBlack: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.Black
    ),
    val quasimodaBold: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.Bold
    ),
    val quasimodaSemiBold: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.SemiBold
    ),
    val quasimodaMedium: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.Medium
    ),
    val quasimodaRegular: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.Normal
    ),
    val quasimodaLight: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.Light
    ),
    val quasimodaThin: TextStyle = quasimoda.copy(
        fontWeight = FontWeight.Thin
    ),
    val fenwickBold12: TextStyle = fenwickBold.copy(
        fontSize = 12.sp,
    ),
    val fenwickBold14: TextStyle = fenwickBold.copy(
        fontSize = 14.sp
    ),
    val fenwickBold16: TextStyle = fenwickBold.copy(
        fontSize = 16.sp
    ),
    val fenwickBold18: TextStyle = fenwickBold.copy(
        fontSize = 18.sp
    ),
    val fenwickBold20: TextStyle = fenwickBold.copy(
        fontSize = 20.sp
    ),
    val fenwickBold22: TextStyle = fenwickBold.copy(
        fontSize = 22.sp
    ),
    val fenwickBold24: TextStyle = fenwickBold.copy(
        fontSize = 24.sp
    ),
    val fenwickBold26: TextStyle = fenwickBold.copy(
        fontSize = 26.sp
    ),
    val fenwickBold28: TextStyle = fenwickBold.copy(
        fontSize = 28.sp
    ),
    val fenwickBold30: TextStyle = fenwickBold.copy(
        fontSize = 30.sp
    ),
    val fenwickBold32: TextStyle = fenwickBold.copy(
        fontSize = 32.sp
    ),
    val fenwickBold34: TextStyle = fenwickBold.copy(
        fontSize = 34.sp
    ),
    val fenwickBold36: TextStyle = fenwickBold.copy(
        fontSize = 36.sp
    ),
    val fenwickBold40: TextStyle = fenwickBold.copy(
        fontSize = 40.sp
    ),
    val fenwickRegular12: TextStyle = fenwickRegular.copy(
        fontSize = 12.sp,
    ),
    val fenwickRegular14: TextStyle = fenwickRegular.copy(
        fontSize = 14.sp
    ),
    val fenwickRegular16: TextStyle = fenwickRegular.copy(
        fontSize = 16.sp
    ),
    val fenwickRegular18: TextStyle = fenwickRegular.copy(
        fontSize = 18.sp
    ),
    val fenwickRegular20: TextStyle = fenwickRegular.copy(
        fontSize = 20.sp
    ),
    val fenwickRegular22: TextStyle = fenwickRegular.copy(
        fontSize = 22.sp
    ),
    val fenwickRegular24: TextStyle = fenwickRegular.copy(
        fontSize = 24.sp
    ),
    val fenwickRegular26: TextStyle = fenwickRegular.copy(
        fontSize = 26.sp
    ),
    val fenwickRegular28: TextStyle = fenwickRegular.copy(
        fontSize = 28.sp
    ),
    val fenwickRegular30: TextStyle = fenwickRegular.copy(
        fontSize = 30.sp
    ),
    val fenwickRegular32: TextStyle = fenwickRegular.copy(
        fontSize = 32.sp
    ),
    val fenwickRegular34: TextStyle = fenwickRegular.copy(
        fontSize = 34.sp
    ),
    val fenwickRegular36: TextStyle = fenwickRegular.copy(
        fontSize = 36.sp
    ),
    val fenwickLight12: TextStyle = fenwickLight.copy(
        fontSize = 12.sp,
    ),
    val fenwickLight14: TextStyle = fenwickLight.copy(
        fontSize = 14.sp
    ),
    val fenwickLight16: TextStyle = fenwickLight.copy(
        fontSize = 16.sp
    ),
    val fenwickLight18: TextStyle = fenwickLight.copy(
        fontSize = 18.sp
    ),
    val fenwickLight20: TextStyle = fenwickLight.copy(
        fontSize = 20.sp
    ),
    val fenwickLight22: TextStyle = fenwickLight.copy(
        fontSize = 22.sp
    ),
    val fenwickLight24: TextStyle = fenwickLight.copy(
        fontSize = 24.sp
    ),
    val fenwickLight26: TextStyle = fenwickLight.copy(
        fontSize = 26.sp
    ),
    val fenwickLight28: TextStyle = fenwickLight.copy(
        fontSize = 28.sp
    ),
    val fenwickLight30: TextStyle = fenwickLight.copy(
        fontSize = 30.sp
    ),
    val fenwickLight32: TextStyle = fenwickLight.copy(
        fontSize = 32.sp
    ),
    val fenwickLight34: TextStyle = fenwickLight.copy(
        fontSize = 34.sp
    ),
    val fenwickLight36: TextStyle = fenwickLight.copy(
        fontSize = 36.sp
    ),
    val quasimodaBlack12: TextStyle = quasimodaBlack.copy(
        fontSize = 12.sp,
    ),
    val quasimodaBlack14: TextStyle = quasimodaBlack.copy(
        fontSize = 14.sp
    ),
    val quasimodaBlack16: TextStyle = quasimodaBlack.copy(
        fontSize = 16.sp
    ),
    val quasimodaBlack18: TextStyle = quasimodaBlack.copy(
        fontSize = 18.sp
    ),
    val quasimodaBlack20: TextStyle = quasimodaBlack.copy(
        fontSize = 20.sp
    ),
    val quasimodaBlack22: TextStyle = quasimodaBlack.copy(
        fontSize = 22.sp
    ),
    val quasimodaBlack24: TextStyle = quasimodaBlack.copy(
        fontSize = 24.sp
    ),
    val quasimodaBlack26: TextStyle = quasimodaBlack.copy(
        fontSize = 26.sp
    ),
    val quasimodaBlack28: TextStyle = quasimodaBlack.copy(
        fontSize = 28.sp
    ),
    val quasimodaBlack30: TextStyle = quasimodaBlack.copy(
        fontSize = 30.sp
    ),
    val quasimodaBlack32: TextStyle = quasimodaBlack.copy(
        fontSize = 32.sp
    ),
    val quasimodaBlack34: TextStyle = quasimodaBlack.copy(
        fontSize = 34.sp
    ),
    val quasimodaBlack36: TextStyle = quasimodaBlack.copy(
        fontSize = 36.sp
    ),
    val quasimodaBold12: TextStyle = quasimodaBold.copy(
        fontSize = 12.sp
    ),
    val quasimodaBold14: TextStyle = quasimodaBold.copy(
        fontSize = 14.sp
    ),
    val quasimodaBold16: TextStyle = quasimodaBold.copy(
        fontSize = 16.sp
    ),
    val quasimodaBold18: TextStyle = quasimodaBold.copy(
        fontSize = 18.sp
    ),
    val quasimodaBold20: TextStyle = quasimodaBold.copy(
        fontSize = 20.sp
    ),
    val quasimodaBold22: TextStyle = quasimodaBold.copy(
        fontSize = 22.sp
    ),
    val quasimodaBold24: TextStyle = quasimodaBold.copy(
        fontSize = 24.sp
    ),
    val quasimodaBold26: TextStyle = quasimodaBold.copy(
        fontSize = 26.sp
    ),
    val quasimodaBold28: TextStyle = quasimodaBold.copy(
        fontSize = 28.sp
    ),
    val quasimodaBold30: TextStyle = quasimodaBold.copy(
        fontSize = 30.sp
    ),
    val quasimodaBold32: TextStyle = quasimodaBold.copy(
        fontSize = 32.sp
    ),
    val quasimodaBold34: TextStyle = quasimodaBold.copy(
        fontSize = 34.sp
    ),
    val quasimodaBold36: TextStyle = quasimodaBold.copy(
        fontSize = 36.sp
    ),
    val quasimodaBold48: TextStyle = quasimodaBold.copy(
        fontSize = 48.sp
    ),
    val quasimodaSemiBold12: TextStyle = quasimodaSemiBold.copy(
        fontSize = 12.sp
    ),
    val quasimodaSemiBold14: TextStyle = quasimodaSemiBold.copy(
        fontSize = 14.sp
    ),
    val quasimodaSemiBold16: TextStyle = quasimodaSemiBold.copy(
        fontSize = 16.sp
    ),
    val quasimodaSemiBold18: TextStyle = quasimodaSemiBold.copy(
        fontSize = 18.sp
    ),
    val quasimodaSemiBold20: TextStyle = quasimodaSemiBold.copy(
        fontSize = 20.sp
    ),
    val quasimodaSemiBold22: TextStyle = quasimodaSemiBold.copy(
        fontSize = 22.sp
    ),
    val quasimodaSemiBold24: TextStyle = quasimodaSemiBold.copy(
        fontSize = 24.sp
    ),
    val quasimodaSemiBold26: TextStyle = quasimodaSemiBold.copy(
        fontSize = 26.sp
    ),
    val quasimodaSemiBold28: TextStyle = quasimodaSemiBold.copy(
        fontSize = 28.sp
    ),
    val quasimodaSemiBold30: TextStyle = quasimodaSemiBold.copy(
        fontSize = 30.sp
    ),
    val quasimodaSemiBold32: TextStyle = quasimodaSemiBold.copy(
        fontSize = 32.sp
    ),
    val quasimodaSemiBold34: TextStyle = quasimodaSemiBold.copy(
        fontSize = 34.sp
    ),
    val quasimodaSemiBold36: TextStyle = quasimodaSemiBold.copy(
        fontSize = 36.sp
    ),
    val quasimodaSemiBold48: TextStyle = quasimodaSemiBold.copy(
        fontSize = 48.sp
    ),
    val quasimodaMedium12: TextStyle = quasimodaMedium.copy(
        fontSize = 12.sp
    ),
    val quasimodaMedium14: TextStyle = quasimodaMedium.copy(
        fontSize = 14.sp
    ),
    val quasimodaMedium16: TextStyle = quasimodaMedium.copy(
        fontSize = 16.sp
    ),
    val quasimodaMedium18: TextStyle = quasimodaMedium.copy(
        fontSize = 18.sp
    ),
    val quasimodaMedium20: TextStyle = quasimodaMedium.copy(
        fontSize = 20.sp
    ),
    val quasimodaMedium22: TextStyle = quasimodaMedium.copy(
        fontSize = 22.sp
    ),
    val quasimodaMedium24: TextStyle = quasimodaMedium.copy(
        fontSize = 24.sp
    ),
    val quasimodaMedium26: TextStyle = quasimodaMedium.copy(
        fontSize = 26.sp
    ),
    val quasimodaMedium28: TextStyle = quasimodaMedium.copy(
        fontSize = 28.sp
    ),
    val quasimodaMedium30: TextStyle = quasimodaMedium.copy(
        fontSize = 30.sp
    ),
    val quasimodaMedium32: TextStyle = quasimodaMedium.copy(
        fontSize = 32.sp
    ),
    val quasimodaMedium34: TextStyle = quasimodaMedium.copy(
        fontSize = 34.sp
    ),
    val quasimodaMedium36: TextStyle = quasimodaMedium.copy(
        fontSize = 36.sp
    ),
    val quasimodaRegular12: TextStyle = quasimodaRegular.copy(
        fontSize = 12.sp
    ),
    val quasimodaRegular14: TextStyle = quasimodaRegular.copy(
        fontSize = 14.sp
    ),
    val quasimodaRegular16: TextStyle = quasimodaRegular.copy(
        fontSize = 16.sp
    ),
    val quasimodaRegular18: TextStyle = quasimodaRegular.copy(
        fontSize = 18.sp
    ),
    val quasimodaRegular20: TextStyle = quasimodaRegular.copy(
        fontSize = 20.sp
    ),
    val quasimodaRegular22: TextStyle = quasimodaRegular.copy(
        fontSize = 22.sp
    ),
    val quasimodaRegular24: TextStyle = quasimodaRegular.copy(
        fontSize = 24.sp
    ),
    val quasimodaRegular26: TextStyle = quasimodaRegular.copy(
        fontSize = 26.sp
    ),
    val quasimodaRegular28: TextStyle = quasimodaRegular.copy(
        fontSize = 28.sp
    ),
    val quasimodaRegular30: TextStyle = quasimodaRegular.copy(
        fontSize = 30.sp
    ),
    val quasimodaRegular32: TextStyle = quasimodaRegular.copy(
        fontSize = 32.sp
    ),
    val quasimodaRegular34: TextStyle = quasimodaRegular.copy(
        fontSize = 34.sp
    ),
    val quasimodaRegular36: TextStyle = quasimodaRegular.copy(
        fontSize = 36.sp
    ),
    val quasimodaLight12: TextStyle = quasimodaLight.copy(
        fontSize = 12.sp
    ),
    val quasimodaLight14: TextStyle = quasimodaLight.copy(
        fontSize = 14.sp
    ),
    val quasimodaLight16: TextStyle = quasimodaLight.copy(
        fontSize = 16.sp
    ),
    val quasimodaLight18: TextStyle = quasimodaLight.copy(
        fontSize = 18.sp
    ),
    val quasimodaLight20: TextStyle = quasimodaLight.copy(
        fontSize = 20.sp
    ),
    val quasimodaLight22: TextStyle = quasimodaLight.copy(
        fontSize = 22.sp
    ),
    val quasimodaLight24: TextStyle = quasimodaLight.copy(
        fontSize = 24.sp
    ),
    val quasimodaLight26: TextStyle = quasimodaLight.copy(
        fontSize = 26.sp
    ),
    val quasimodaLight28: TextStyle = quasimodaLight.copy(
        fontSize = 28.sp
    ),
    val quasimodaLight30: TextStyle = quasimodaLight.copy(
        fontSize = 30.sp
    ),
    val quasimodaLight32: TextStyle = quasimodaLight.copy(
        fontSize = 32.sp
    ),
    val quasimodaLight34: TextStyle = quasimodaLight.copy(
        fontSize = 34.sp
    ),
    val quasimodaLight36: TextStyle = quasimodaLight.copy(
        fontSize = 36.sp
    ),
    val quasimodaThin12: TextStyle = quasimodaThin.copy(
        fontSize = 12.sp
    ),
    val quasimodaThin14: TextStyle = quasimodaThin.copy(
        fontSize = 14.sp
    ),
    val quasimodaThin16: TextStyle = quasimodaThin.copy(
        fontSize = 16.sp
    ),
    val quasimodaThin18: TextStyle = quasimodaThin.copy(
        fontSize = 18.sp
    ),
    val quasimodaThin20: TextStyle = quasimodaThin.copy(
        fontSize = 20.sp
    ),
    val quasimodaThin22: TextStyle = quasimodaThin.copy(
        fontSize = 22.sp
    ),
    val quasimodaThin24: TextStyle = quasimodaThin.copy(
        fontSize = 24.sp
    ),
    val quasimodaThin26: TextStyle = quasimodaThin.copy(
        fontSize = 26.sp
    ),
    val quasimodaThin28: TextStyle = quasimodaThin.copy(
        fontSize = 28.sp
    ),
    val quasimodaThin30: TextStyle = quasimodaThin.copy(
        fontSize = 30.sp
    ),
    val quasimodaThin32: TextStyle = quasimodaThin.copy(
        fontSize = 32.sp
    ),
    val quasimodaThin34: TextStyle = quasimodaThin.copy(
        fontSize = 34.sp
    ),
    val quasimodaThin36: TextStyle = quasimodaThin.copy(
        fontSize = 36.sp
    ),
)

internal val LocalAppTypography = staticCompositionLocalOf { AppTypography() }