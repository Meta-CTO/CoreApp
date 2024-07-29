package com.metacto.core.utils.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

fun String.format(vararg args: Any): String {
    var formattedString = this
    args.forEach { arg ->
        formattedString = formattedString.replaceFirst("%s", arg.toString())
    }
    return formattedString
}

fun String?.ifNotNullOrEmpty(callback: (String) -> Unit) {
    if (!this.isNullOrEmpty()) {
        callback(this)
    }
}

fun String.removeAllWhiteSpaces(): String {
    return this.replace(" ", "")
}

fun String.isImageUrl(): Boolean {
    return Regex("([^\\s]+(\\.(?i)(jpe?g|png|gif|svg|bmp))$)").matches(this)
}

fun String.removeLeadingZeros(): String {
    var i = 0
    while (i < this.length && this[i] == '0') i++
    return if (i == this.length) "0" else this.substring(i)
}

private val PASSWORD_REGEX = Regex(
    "^(?=.*?[A-Z\u0621-\u064A])(?=.*?[a-z\u0621-\u064A])(?=.*?[0-9])(?=.*?[@_&,.:$!-]).{8,}\$"
)

fun String.isValidPassword(): Boolean {
    return this.isNotEmpty() && PASSWORD_REGEX.matches(this)
}

private val EMAIL_REGEX = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
fun String.isValidEmail(): Boolean {
    return !isNullOrEmpty() && EMAIL_REGEX.matches(this.trim())
}

inline fun String.remove(substring: String) = replace(substring, "")

fun String?.nullIfEmpty() = if (isNullOrEmpty()) null else this

fun String.appendRequiredAsterisk() = "$this*"

fun String.dashIfEmpty() = this.ifEmpty { "-" }

fun String?.dashIfNullOrEmpty() = if (this?.isNotEmpty() == true) this else "-"

fun String.doubleDashIfEmpty() = this.ifEmpty { "--" }

fun String?.orDoubleDash() = if (this?.isNotEmpty() == true) this else "--"

fun String.takeIfNotEmpty() = this.takeIf { it.isNotEmpty() }

fun Char?.orEmpty(): String = this?.toString() ?: ""

fun String.getValidUrl(): String {
    return if (!this.startsWith("http://") && !this.startsWith("https://")) {
        "http://$this"
    } else {
        this
    }
}

fun String.capitalizeFirstLetter(): String {
    return try {
        this[0].uppercase() + substring(1).lowercase()
    } catch (e: Throwable) {
        this
    }
}

private val nonNumericRegex = Regex("[^0-9]")
fun String.removeAllNonNumeric(): String {
    return this.replace(nonNumericRegex, "")
}

fun String.formatWithMask(mask: String, maskChar: Char): String {
    val maxLength = mask.count { it == maskChar }
    val trimmed = this.take(maxLength)
    if (trimmed.isEmpty()) return ""

    return buildString {
        var maskIndex = 0
        var textIndex = 0
        while (textIndex < trimmed.length && maskIndex < mask.length) {
            if (mask[maskIndex] != maskChar) {
                val nextDigitIndex = mask.indexOf(maskChar, maskIndex)
                append(mask.substring(maskIndex, nextDigitIndex))
                maskIndex = nextDigitIndex
            }
            append(trimmed[textIndex++])
            maskIndex++
        }
    }
}

fun AnnotatedString.formatWithMask(mask: String, maskChar: Char): AnnotatedString {
    return buildAnnotatedString {
        val formatted = text.formatWithMask(
            mask = mask,
            maskChar = maskChar
        )

        append(formatted)
    }
}

fun String.removeLastChar(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(0, this.length - 1)
}

fun CharSequence.toAnnotatedString(): AnnotatedString {
    return AnnotatedString(this.toString())
}

fun AnnotatedString.filterDigits(): AnnotatedString {
    return this.filter { it.isDigit() }.toAnnotatedString()
}

fun String.asInt(): Int {
    return try {
        this.toInt()
    } catch (throwable: Throwable) {
        0
    }
}

fun String?.containsAny(strings: List<String>): Boolean {
    return containsAny(
        strings = strings,
        ignoreCase = false
    )
}

fun String?.containsAnyIgnoringCase(strings: List<String>): Boolean {
    return containsAny(
        strings = strings,
        ignoreCase = true
    )
}

private fun String?.containsAny(
    strings: List<String>,
    ignoreCase: Boolean = false
): Boolean {
    if (this == null) return false

    strings.forEach { str ->
        if (this.contains(str, ignoreCase)) {
            return true
        }
    }

    return false
}

fun String.toColor(): Color {
    val colorString = this.removePrefix("#")
    val colorInt = colorString.toLong(16)

    return if (colorString.length == 8) {
        Color(colorInt)
    } else {
        Color(colorInt or 0xFF000000)
    }
}

fun String.isValidCardNumber(): Boolean {
    // credit card regex
    val creditCardRegex = Regex(
        "^4[0-9]{12}(?:[0-9]{3})?$" + // Visa
                "|^5[1-5][0-9]{14}$" + // MasterCard
                "|^3[47][0-9]{13}$" + // American Express
                "|^3(?:0[0-5]|[68][0-9])[0-9]{11}$" + // Diners Club
                "|^6(?:011|5[0-9]{2})[0-9]{12}$" + // Discover
                "|^(?:2131|1800|35\\d{3})\\d{11}$" // JCB
    )

    // return if the card number matches the regex
    return this.matches(creditCardRegex)
}
