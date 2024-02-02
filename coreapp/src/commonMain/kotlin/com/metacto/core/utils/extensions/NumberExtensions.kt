package com.metacto.core.utils.extensions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

fun Int.isMoreThanOne() = this > 1

fun Dp?.orZero() = this ?: 0.dp

expect fun Double.formatToMaxOneDecimal(): String

expect fun Double.formatToMaxTwoDecimals(): String

expect fun Double.formatToComma(): String

expect fun Double.formatToCurrency(): String

expect fun Float.formatToMaxOneDecimal(): String

expect fun Float.formatToMaxTwoDecimals(): String

expect fun Float.formatToComma(): String

expect fun Float.formatToCurrency(): String