package com.metacto.core.ui.extensions

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

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