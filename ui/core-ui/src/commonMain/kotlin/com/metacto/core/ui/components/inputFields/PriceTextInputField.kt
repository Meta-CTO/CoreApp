package com.metacto.core.ui.components.inputFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.extensions.formatToMaxDecimals
import com.metacto.core.extensions.orZero
import com.metacto.core.extensions.removeAllNonDecimal
import com.metacto.core.ui.theme.CoreTheme.colors
import com.metacto.core.ui.theme.CoreTheme.shapes
import com.metacto.core.ui.theme.CoreTheme.spacings
import com.metacto.core.ui.theme.CoreTheme.typography
import kotlin.time.Duration

@Composable
fun PriceTextInputField(
    modifier: Modifier = Modifier,
    backgroundColor: Color = colors.priceTextInputField.bgColor,
    backgroundShape: RoundedCornerShape = shapes.priceTextInputField.shape,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
    price: String? = null,
    isStaticLabel: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    textColor: Color = colors.priceTextInputField.textColor,
    textStyle: TextStyle = typography.priceTextInputField.textStyle.copy(color = textColor),
    visualTransformationSpanStyle: SpanStyle? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onPriceChange: ((String?) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = spacings.priceTextInputField.endIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = spacings.priceTextInputField.startIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color? = colors.priceTextInputField.iconColor,
    focusedBorderColor: Color = colors.priceTextInputField.focusedBorderColor,
    unFocusedBorderColor: Color = colors.priceTextInputField.unFocusedBorderColor,
    placeholderTextStyle: TextStyle = typography.priceTextInputField.placeholderTextStyle,
    placeholderTextColor: Color = colors.priceTextInputField.placeholderColor,
    placeholderMaxLines: Int = 1,
    labelTextStyle: TextStyle = typography.priceTextInputField.labelTextStyle,
    labelTextColor: Color = colors.priceTextInputField.labelColor,
    errorTextStyle: TextStyle = typography.priceTextInputField.errorTextStyle,
    errorTextColor: Color = colors.priceTextInputField.errorColor,
    maxLength: Int = Int.MAX_VALUE,
    minHeight: Dp = spacings.priceTextInputField.minHeight,
    elevation: Dp = spacings.priceTextInputField.elevation,
    shadowColor: Color = colors.priceTextInputField.shadowColor,
    requestFocus: Boolean = false,
    requestFocusDelay: Duration = DEFAULT_REQUEST_FOCUS_DELAY,
    error: String? = null,
    textAlign: TextAlign? = null,
    allowDecimal: Boolean = false,
    maxAllowedDecimals: Int = 2,
    floatingLabelSpacing: Dp = spacings.priceTextInputField.floatingLabelSpacing,
    contentPadding: PaddingValues = spacings.priceTextInputField.contentPadding,
    focusedBorderThickness: Dp = spacings.priceTextInputField.focusedBorderThickness,
    unfocusedBorderThickness: Dp = spacings.priceTextInputField.unfocusedBorderThickness,
    minWidth: Dp = spacings.priceTextInputField.minWidth,
) {
    val text = if (!allowDecimal) {
        price?.toIntOrNull().orZero().toString()
    } else {
        price.orEmpty()
    }

    BaseTextInputField(
        modifier = modifier,
        text = text,
        isStaticLabel = isStaticLabel,
        placeholder = placeholder.orEmpty(),
        textColor = textColor,
        readOnly = readOnly,
        keyboardType = if (allowDecimal) KeyboardType.Decimal else KeyboardType.Number,
        textStyle = textStyle,
        visualTransformation = CurrencyAmountInputVisualTransformation(
            style = visualTransformationSpanStyle,
            maxAllowedDecimals = maxAllowedDecimals
        ),
        maxLines = 1,
        singleLine = true,
        capitalization = KeyboardCapitalization.None,
        enabled = enabled,
        shape = backgroundShape,
        allowDigitsOnly = !allowDecimal,
        backgroundColor = backgroundColor,
        onClick = onClick,
        imeAction = imeAction,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        endIconSize = endIconSize,
        shadowColor = shadowColor,
        onEndIconClick = onEndIconClick,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        startIconSize = startIconSize,
        onStartIconClick = onStartIconClick,
        iconTintColor = iconTintColor,
        focusedBorderColor = focusedBorderColor,
        unFocusedBorderColor = unFocusedBorderColor,
        placeholderTextStyle = placeholderTextStyle,
        labelTextStyle = labelTextStyle,
        errorTextStyle = errorTextStyle,
        placeholderTextColor = placeholderTextColor,
        elevation = elevation,
        labelTextColor = labelTextColor,
        errorTextColor = errorTextColor,
        maxLength = maxLength,
        label = label,
        error = error,
        minHeight = minHeight,
        requestFocus = requestFocus,
        requestFocusDelay = requestFocusDelay,
        keyboardActions = keyboardActions,
        placeholderMaxLines = placeholderMaxLines,
        textAlign = textAlign,
        floatingLabelSpacing = floatingLabelSpacing,
        contentPadding = contentPadding,
        focusedBorderThickness = focusedBorderThickness,
        unfocusedBorderThickness = unfocusedBorderThickness,
        minLines = 1,
        minWidth = minWidth,
        onValueChange = { value ->
            if (value.isEmpty()) {
                onPriceChange?.invoke(null)
            } else {
                val digitValue = value.removeAllNonDecimal().formatToMaxDecimals(maxAllowedDecimals)
                onPriceChange?.invoke(digitValue)
            }
        }
    )
}