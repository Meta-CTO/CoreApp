package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PriceTextInputField(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CoreTheme.colors.white,
    backgroundShape: Shape = CoreTheme.shapes.xSmall,
    priceFieldModifier: Modifier = Modifier,
    price: Int? = null,
    placeholder: String? = null,
    textColor: Color = CoreTheme.colors.black,
    textStyle: TextStyle = CoreTheme.typography.bodyMedium.copy(color = textColor),
    visualTransformationSpanStyle: SpanStyle? = null,
    readOnly: Boolean = false,
    onPriceChange: ((Int?) -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Next
) {
    Box(
        modifier = modifier.background(color = backgroundColor, shape = backgroundShape),
        contentAlignment = Alignment.Center
    ) {
        InlineInputField(
            modifier = priceFieldModifier.align(Alignment.CenterEnd),
            text = price?.toString().orEmpty(),
            placeholder = placeholder.orEmpty(),
            textColor = textColor,
            readOnly = readOnly,
            keyboardType = KeyboardType.Number,
            textStyle = textStyle,
            visualTransformation = CurrencyAmountInputVisualTransformation(style = visualTransformationSpanStyle),
            maxLines = 1,
            imeAction = imeAction,
            onValueChange = {
                if (it.isEmpty()) {
                    onPriceChange?.invoke(null)
                } else {
                    val digitValue = it.toIntOrNull() ?: return@InlineInputField
                    onPriceChange?.invoke(digitValue)
                }
            }
        )
    }
}