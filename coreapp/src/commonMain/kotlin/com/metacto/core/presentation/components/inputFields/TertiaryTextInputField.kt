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
    minHeight: Dp = CoreTheme.spacings.tertiaryTextInputFieldMinHeight,
    error: String? = null,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = CoreTheme.spacings.tertiaryTextInputFieldEndIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = CoreTheme.spacings.tertiaryTextInputFieldStartIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color = CoreTheme.colors.tertiaryTextInputFieldIconColor,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    shape: RoundedCornerShape = CoreTheme.shapes.tertiaryTextInputFieldShape,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = false,
    requestFocus: Boolean = false,
    textStyle: TextStyle = CoreTheme.typography.tertiaryTextInputFieldTextStyle,
    textColor: Color = CoreTheme.colors.tertiaryTextInputFieldTextColor,
    placeholderTextStyle: TextStyle = CoreTheme.typography.tertiaryTextInputFieldPlaceholderTextStyle,
    placeholderTextColor: Color = CoreTheme.colors.tertiaryTextInputFieldPlaceholderColor,
    labelTextStyle: TextStyle = CoreTheme.typography.tertiaryTextInputFieldLabelTextStyle,
    labelTextColor: Color = CoreTheme.colors.tertiaryTextInputFieldLabelColor,
    errorTextStyle: TextStyle = CoreTheme.typography.tertiaryTextInputFieldErrorTextStyle,
    errorTextColor: Color = CoreTheme.colors.tertiaryTextInputFieldErrorColor,
    focusedBorderColor: Color = CoreTheme.colors.tertiaryTextInputFieldFocusedBorderColor,
    unFocusedBorderColor: Color = CoreTheme.colors.tertiaryTextInputFieldUnFocusedBorderColor,
    backgroundColor: Color = CoreTheme.colors.tertiaryTextInputFieldBg,
    ) {
    BaseTextInputField(
        requestFocus = requestFocus,
        text = text,
        label = label,
        onValueChange = onValueChange,
        onClick = onClick,
        modifier = modifier,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
        capitalization = capitalization,
        singleLine = singleLine,
        enabled = enabled,
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