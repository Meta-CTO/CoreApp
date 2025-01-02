package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import kotlin.time.Duration

@Composable
fun TertiaryTextInputField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    minHeight: Dp = CoreTheme.spacings.tertiaryTextInputField.minHeight,
    error: String? = null,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = CoreTheme.spacings.tertiaryTextInputField.endIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = CoreTheme.spacings.tertiaryTextInputField.startIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color? = CoreTheme.colors.tertiaryTextInputField.iconColor,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    shape: RoundedCornerShape = CoreTheme.shapes.tertiaryTextInputField.shape,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = false,
    requestFocus: Boolean = false,
    requestFocusDelay: Duration = DEFAULT_REQUEST_FOCUS_DELAY,
    textStyle: TextStyle = CoreTheme.typography.tertiaryTextInputField.textStyle,
    textColor: Color = CoreTheme.colors.tertiaryTextInputField.textColor,
    placeholderTextStyle: TextStyle = CoreTheme.typography.tertiaryTextInputField.placeholderTextStyle,
    placeholderTextColor: Color = CoreTheme.colors.tertiaryTextInputField.placeholderColor,
    labelTextStyle: TextStyle = CoreTheme.typography.tertiaryTextInputField.labelTextStyle,
    labelTextColor: Color = CoreTheme.colors.tertiaryTextInputField.labelColor,
    errorTextStyle: TextStyle = CoreTheme.typography.tertiaryTextInputField.errorTextStyle,
    errorTextColor: Color = CoreTheme.colors.tertiaryTextInputField.errorColor,
    focusedBorderColor: Color = CoreTheme.colors.tertiaryTextInputField.focusedBorderColor,
    unFocusedBorderColor: Color = CoreTheme.colors.tertiaryTextInputField.unFocusedBorderColor,
    backgroundColor: Color = CoreTheme.colors.tertiaryTextInputField.bgColor,
    elevation: Dp = CoreTheme.spacings.tertiaryTextInputField.elevation,
    shadowColor: Color = CoreTheme.colors.secondaryTextInputField.shadowColor
) {
    BaseTextInputField(
        requestFocus = requestFocus,
        requestFocusDelay = requestFocusDelay,
        text = text,
        label = label,
        onValueChange = onValueChange,
        onClick = onClick,
        modifier = modifier,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
        capitalization = capitalization,
        elevation = elevation,
        singleLine = singleLine,
        enabled = enabled,
        shadowColor = shadowColor,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        maxLength = maxLength,
        maxLines = maxLines,
        minHeight = minHeight,
        error = error,
        endIconVector = endIconVector,
        endIconPainter = endIconPainter,
        endIconSize = endIconSize,
        onEndIconClick = onEndIconClick,
        startIconVector = startIconVector,
        startIconPainter = startIconPainter,
        startIconSize = startIconSize,
        onStartIconClick = onStartIconClick,
        iconTintColor = iconTintColor,
        placeholder = placeholder,
        placeholderMaxLines = placeholderMaxLines,
        shape = shape,
        allowDigitsOnly = allowDigitsOnly,
        isStaticLabel = isStaticLabel,
        backgroundColor = backgroundColor,
        focusedBorderColor = focusedBorderColor,
        unFocusedBorderColor = unFocusedBorderColor,
        textStyle = textStyle,
        textAlign = textAlign,
        textColor = textColor,
        placeholderTextStyle = placeholderTextStyle,
        placeholderTextColor = placeholderTextColor,
        labelTextStyle = labelTextStyle,
        labelTextColor = labelTextColor,
        errorTextStyle = errorTextStyle,
        errorTextColor = errorTextColor
    )
}