package com.metacto.core.extensions

import com.metacto.core.domain.models.JwtPayload
import io.ktor.util.decodeBase64String
import kotlinx.serialization.json.Json

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

private val nonDecimalRegex = Regex("[^0-9.]")

fun String.removeAllNonDecimal(): String {
    return this.replace(nonDecimalRegex, "").replace(Regex("(\\..*?)\\."), "$1")
}

fun String.formatToCurrency(
    currency: String = "$",
    addSpace: Boolean = true,
    allowedMaxDecimals: Int = 2
): String {
    val updatedValue = this.formatToMaxDecimals(allowedMaxDecimals)

    return if (addSpace) {
        "$currency $updatedValue"
    } else {
        "$currency$updatedValue"
    }
}

fun String.formatToMaxDecimals(allowedMaxDecimals: Int): String {
    val regex = Regex("(\\.\\d{$allowedMaxDecimals})\\d+")
    return regex.replace(this) { matchResult ->
        matchResult.groupValues[1]
    }
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

fun String.removeLastChar(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(0, this.length - 1)
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

fun String.isValidUrl(): Boolean {
    val pattern = "^(https?|ftp)://[a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=%]+$"
    return Regex(pattern).matches(this)
}

fun String.isLocalFile(): Boolean {
    return startsWith("file://") || startsWith("/")
}

fun String.splitAtIndexSafely(index: Int): Pair<String, String> {
    if (this.isEmpty()) {
        return Pair("", "")
    }

    if (index <= 0) {
        return Pair("", this)
    }

    if (index >= this.length) {
        return Pair(this, "")
    }

    return Pair(
        this.substring(0, index),
        this.substring(index)
    )
}

fun String.matchWithWildcard(pattern: String): Boolean {
    val regex = pattern.replace("*", ".*").toRegex()
    return matches(regex)
}

fun String.cleanHtml(): String {
    return this
        .replace(Regex("<p>|</p>"), "\n") // Replace <p> with new lines
        .replace(Regex("<[^>]*>"), "") // Remove all other HTML tags
        .replace("&nbsp;", " ") // Replace non-breaking spaces
        .replace("&mdash;", "—") // Replace em dash
        .replace("&hellip;", "...") // Replace ellipsis
        .replace("&rsquo;", "'") // Replace right single quote
        .replace("&ldquo;", "\"") // Replace left double quote
        .replace("&rdquo;", "\"") // Replace right double quote
        .replace("&lsquo;", "'") // Replace left single quote
        .replace("&amp;", "&") // Replace ampersand
        .replace("&lt;", "<") // Replace less than
        .replace("&gt;", ">") // Replace greater than
        .replace("\r\n", "\n") // Normalize line breaks
        .replace(Regex("\\n+"), "\n\n") // Remove multiple new lines
        .split("\n")
        .joinToString("\n") { it.trimStart() } // Join lines back
        .trim()
}

fun String.decodeJwt(): JwtPayload? {
    return try {
        val parts = this.split(".")
        val payloadBase64 = parts.getOrNull(1)
        val decodedPayload = payloadBase64?.decodeBase64String() ?: return null
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<JwtPayload>(decodedPayload)
    } catch (_: Throwable) {
        null
    }
}