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
fun PrimaryTextInputField(
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
    minHeight: Dp = CoreTheme.spacings.primaryTextInputFieldMinHeight,
    error: String? = null,
    textStyle: TextStyle = CoreTheme.typography.inputFieldText,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = CoreTheme.spacings.primaryTextInputFieldEndIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = CoreTheme.spacings.primaryTextInputFieldStartIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color = CoreTheme.colors.primaryTextInputFieldIconColor,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    shape: RoundedCornerShape = CoreTheme.shapes.primaryTextInputFieldShape,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = false,
    requestFocus: Boolean = false,
    backgroundColor: Color = CoreTheme.colors.primaryTextInputFieldBg,
    focusedBorderColor: Color = CoreTheme.colors.primaryTextInputFieldFocusedBorderColor,
    unFocusedBorderColor: Color = CoreTheme.colors.primaryTextInputFieldUnFocusedBorderColor,
    textColor: Color = CoreTheme.colors.primaryTextInputFieldTextColor,
    placeholderTextStyle: TextStyle = CoreTheme.typography.primaryTextInputFieldPlaceholderTextStyle,
    placeholderTextColor: Color = CoreTheme.colors.primaryTextInputFieldPlaceholderColor,
    labelTextStyle: TextStyle = CoreTheme.typography.primaryTextInputFieldLabelTextStyle,
    labelTextColor: Color = CoreTheme.colors.primaryTextInputFieldLabelColor,
    errorTextStyle: TextStyle = CoreTheme.typography.primaryTextInputFieldErrorTextStyle,
    errorTextColor: Color = CoreTheme.colors.primaryTextInputFieldErrorColor,
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
        labelTextStyle = labelTextStyle,
        errorTextStyle = errorTextStyle,
        placeholderTextColor = placeholderTextColor,
        labelTextColor = labelTextColor,
        errorTextColor = errorTextColor,
    )
}