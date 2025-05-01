package com.metacto.core.ui.components.inputFields

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.metacto.core.extensions.addList
import com.metacto.core.extensions.formatToCurrency
import com.metacto.core.extensions.orZero
import com.metacto.core.ui.extensions.orZero

class CurrencyAmountInputVisualTransformation(
    private val style: SpanStyle? = null,
    private val currency: String = "$",
    private val addSpaceToFormattedCurrency: Boolean = true,
    private val maxAllowedDecimals: Int = 2
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val inputText = text.text
        val formattedNumber = inputText.formatToCurrency(
            currency = currency,
            addSpace = addSpaceToFormattedCurrency,
            allowedMaxDecimals = maxAllowedDecimals
        )

        val newText = AnnotatedString(
            text = formattedNumber,
            spanStyles = if (style == null) {
                text.spanStyles
            } else {
                listOf(AnnotatedString.Range(style, start = 0, end = 1)).addList(text.spanStyles)
            },
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = FixedCursorOffsetMapping(
            contentLength = inputText.length,
            formattedContentLength = formattedNumber.length.orZero()
        )

        return TransformedText(newText, offsetMapping)
    }

    private class FixedCursorOffsetMapping(
        private val contentLength: Int,
        private val formattedContentLength: Int,
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = formattedContentLength
        override fun transformedToOriginal(offset: Int): Int = contentLength
    }
}