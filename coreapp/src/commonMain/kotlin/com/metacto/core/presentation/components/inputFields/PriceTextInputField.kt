package com.metacto.core.presentation.components.inputFields

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
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PriceTextInputField(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CoreTheme.colors.white,
    backgroundShape: RoundedCornerShape = CoreTheme.shapes.small,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
    price: Int? = null,
    isStaticLabel: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    textColor: Color = CoreTheme.colors.black,
    textStyle: TextStyle = CoreTheme.typography.bodyMedium.copy(color = textColor),
    visualTransformationSpanStyle: SpanStyle? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onPriceChange: ((Int?) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = CoreTheme.spacings.iconSmall,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = CoreTheme.spacings.iconSmall,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color = CoreTheme.colors.tertiary,
    focusedBorderColor: Color = CoreTheme.colors.primaryDark,
    unFocusedBorderColor: Color = CoreTheme.colors.primary,
    placeholderTextStyle: TextStyle = CoreTheme.typography.labelMedium,
    placeholderTextColor: Color = CoreTheme.colors.placeholder,
    placeholderMaxLines: Int = 1,
    labelTextStyle: TextStyle = CoreTheme.typography.labelMedium,
    labelTextColor: Color = CoreTheme.colors.secondary,
    errorTextStyle: TextStyle = CoreTheme.typography.labelMedium,
    errorTextColor: Color = CoreTheme.colors.danger,
    maxLength: Int = Int.MAX_VALUE,
    minHeight: Dp = CoreTheme.spacings.noSpacing,
    requestFocus: Boolean = false,
    error: String? = null,
    textAlign: TextAlign? = null,
) {
    BaseTextInputField(
        modifier = modifier,
        text = price?.toString().orEmpty(),
        isStaticLabel = isStaticLabel,
        placeholder = placeholder.orEmpty(),
        textColor = textColor,
        readOnly = readOnly,
        keyboardType = KeyboardType.Number,
        textStyle = textStyle,
        visualTransformation = CurrencyAmountInputVisualTransformation(style = visualTransformationSpanStyle),
        maxLines = 1,
        singleLine = true,
        capitalization = KeyboardCapitalization.None,
        enabled = enabled,
        shape = backgroundShape,
        allowDigitsOnly = true,
        backgroundColor = backgroundColor,
        onClick = onClick,
        imeAction = imeAction,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        endIconSize = endIconSize,
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
        labelTextColor = labelTextColor,
        errorTextColor = errorTextColor,
        maxLength = maxLength,
        label = label,
        error = error,
        minHeight = minHeight,
        requestFocus = requestFocus,
        keyboardActions = keyboardActions,
        placeholderMaxLines = placeholderMaxLines,
        textAlign = textAlign,
        onValueChange = { value ->
            if (value.isEmpty()) {
                onPriceChange?.invoke(null)
            } else {
                val digitValue = value.toIntOrNull() ?: return@BaseTextInputField
                onPriceChange?.invoke(digitValue)
            }
        }
    )
}