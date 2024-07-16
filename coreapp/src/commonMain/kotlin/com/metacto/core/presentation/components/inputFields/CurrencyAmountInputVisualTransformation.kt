package com.metacto.core.presentation.components.inputFields

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.metacto.core.utils.extensions.addList
import com.metacto.core.utils.extensions.formatToCurrency
import com.metacto.core.utils.extensions.orZero

class CurrencyAmountInputVisualTransformation(private val style: SpanStyle? = null) :
    VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val inputText = text.text
        val formattedNumber = inputText.toFloatOrNull()?.formatToCurrency()

        val newText = AnnotatedString(
            text = formattedNumber.orEmpty(),
            spanStyles = if (style == null) text.spanStyles else listOf(
                AnnotatedString.Range(style, start = 0, end = 1)
            ).addList(text.spanStyles),
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = FixedCursorOffsetMapping(
            contentLength = inputText.length,
            formattedContentLength = formattedNumber?.length.orZero()
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