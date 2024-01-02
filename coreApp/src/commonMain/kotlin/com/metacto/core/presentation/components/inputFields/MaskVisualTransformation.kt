package com.metacto.core.presentation.components.inputFields

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.metacto.core.utils.extensions.formatWithMask

open class MaskVisualTransformation(
    private val mask: String,
    private val maskChar: Char
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = text.formatWithMask(
            mask = mask,
            maskChar = maskChar
        )

        return TransformedText(
            text = formattedText,
            offsetMapping = OffsetMapper(mask, maskChar)
        )
    }

    fun getMaxAllowedLength() = mask.count {
        it == maskChar
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaskVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskChar != other.maskChar) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }

    private class OffsetMapper(
        private val mask: String,
        private val numberChar: Char
    ) : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {
            var noneDigitCount = 0
            var i = 0
            while (i < offset + noneDigitCount) {
                if (mask[i++] != numberChar) noneDigitCount++
            }
            return offset + noneDigitCount
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset - mask.take(offset).count { it != numberChar }
        }
    }
}