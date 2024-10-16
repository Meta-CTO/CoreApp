package com.metacto.core.utils.extensions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.random.Random

fun Int.isOdd(): Boolean = this % 2 != 0

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun Int?.toBoolean() = this == 1

fun Int?.orZero() = this ?: 0

fun Int?.orRandom() = this ?: Random.nextInt()

fun Int?.orOne() = this ?: 1

fun Float?.orZero() = this ?: 0f

fun Double?.orZero() = this ?: 0.toDouble()

fun Long?.orZero() = this ?: 0L

fun Long?.orOne() = this ?: 1L

fun Float.half() = this.div(2)

fun Float.quarter() = this.div(2)

fun Int.half() = this.div(2)

fun Int.quarter() = this.div(2)

fun Int.negative() = this.times(-1)

fun Float.negative() = this.times(-1)

fun Float.inRange(number: Float, tolerance: Int): Boolean {
    return this in (number - tolerance)..(number + tolerance)
}

fun Int.isPositive() = this > 0

fun Int.isNegative() = this < 0

fun Float.isPositive() = this > 0

fun Float.isNegative() = this < 0

fun Double.isPositive() = this > 0

fun Double.isNegative() = this < 0

fun Int.divOrZero(number: Int): Int {
    return if (number == 0) 0
    else this / number
}

fun Int.halfOrZero() = this.divOrZero(2)

fun Int.quarterOrZero() = this.divOrZero(4)

fun Int?.stringOrDash(): String {
    return if (this != null && this != 0) {
        this.toString()
    } else {
        "-"
    }
}

private fun Int.formatSecondsTime(): String {
    val sec = this % 60
    val minutes = (this / 60) % 60
    val hours = (this / 3600)

    val hoursStr = if (hours < 10) "0$hours" else "$hours"
    val minutesStr = if (minutes < 10) "0$minutes" else "$minutes"
    val secondsStr = if (sec < 10) "0$sec" else "$sec"

    return "$hoursStr:$minutesStr:$secondsStr"
}

fun Int.isMoreThanOne() = this > 1

fun Dp?.orZero() = this ?: 0.dp

fun Double.truncate(decimalsCount: Int): Double {
    val multiplier = 10.0.pow(decimalsCount.toDouble())
    return (this * multiplier).toInt() / multiplier
}

fun Float.truncate(decimalsCount: Int): Float {
    val multiplier = 10.0.pow(decimalsCount.toDouble())
    return (this * multiplier).toInt() / multiplier.toFloat()
}

fun Float.formatToMaxOneDecimal(): String {
    return this.toDouble().formatToMaxOneDecimal()
}

fun Float.formatToMaxTwoDecimals(): String {
    return this.toDouble().formatToMaxTwoDecimals()
}

fun Float.formatToComma(): String {
    return this.toDouble().formatToComma()
}

fun Float.formatToCurrency(currency: String = "$", addSpace: Boolean = true): String {
    return this.toDouble().formatToCurrency(
        currency = currency,
        addSpace = addSpace
    )
}

fun Float.format(decimalsCount: Int): String {
    return this.toDouble().format(decimalsCount)
}

fun Double.formatToMaxOneDecimal(): String {
    return this.format(decimalsCount = 1)
}

fun Double.formatToMaxTwoDecimals(): String {
    return this.format(decimalsCount = 2)
}

fun Double.formatToCurrency(currency: String = "$", addSpace: Boolean = true): String {
    val formattedAmount = this.formatToMaxTwoDecimals()
    return if (addSpace) {
        "$currency $formattedAmount"
    } else {
        "$currency$formattedAmount"
    }
}

fun Int.toHex(): String {
    return this.toString(16).padStart(2, '0').uppercase()
}

fun Int.formatSecondsToMMSS(): String {
    val sec = this % 60
    val minutes = (this / 60) % 60

    val minutesStr = if (minutes < 10) "0$minutes" else "$minutes"
    val secondsStr = if (sec < 10) "0$sec" else "$sec"

    return "$minutesStr:$secondsStr"
}

fun Int.formatSecondsToHHMMSS(): String {
    val sec = this % 60
    val minutes = (this / 60) % 60
    val hours = (this / 3600)

    val hoursStr = if (hours < 10) "0$hours" else "$hours"
    val minutesStr = if (minutes < 10) "0$minutes" else "$minutes"
    val secondsStr = if (sec < 10) "0$sec" else "$sec"

    return "$hoursStr:$minutesStr:$secondsStr"
}

expect fun Double.format(decimalsCount: Int): String

expect fun Double.formatToComma(maxFractionCount: Int = 0): String