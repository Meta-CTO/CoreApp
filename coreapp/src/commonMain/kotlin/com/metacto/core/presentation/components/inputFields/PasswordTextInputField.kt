package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PasswordTextInputField(
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
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    minHeight: Dp = CoreTheme.spacings.passwordTextInputField.minHeight,
    error: String? = null,
    endIconSize: Dp = CoreTheme.spacings.passwordTextInputField.endIconSize,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = CoreTheme.spacings.passwordTextInputField.startIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color = CoreTheme.colors.passwordTextInputField.iconColor,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    shape: RoundedCornerShape = CoreTheme.shapes.passwordTextInputField.shape,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = false,
    requestFocus: Boolean = false,
    showPasswordVisibilityToggle: Boolean = false,
    textStyle: TextStyle = CoreTheme.typography.passwordTextInputField.textStyle,
    textColor: Color = CoreTheme.colors.passwordTextInputField.textColor,
    placeholderTextStyle: TextStyle = CoreTheme.typography.passwordTextInputField.placeholderTextStyle,
    placeholderTextColor: Color = CoreTheme.colors.passwordTextInputField.placeholderColor,
    labelTextStyle: TextStyle = CoreTheme.typography.passwordTextInputField.labelTextStyle,
    labelTextColor: Color = CoreTheme.colors.passwordTextInputField.labelColor,
    errorTextStyle: TextStyle = CoreTheme.typography.passwordTextInputField.errorTextStyle,
    errorTextColor: Color = CoreTheme.colors.passwordTextInputField.errorColor,
    focusedBorderColor: Color = CoreTheme.colors.passwordTextInputField.focusedBorderColor,
    unFocusedBorderColor: Color = CoreTheme.colors.passwordTextInputField.unFocusedBorderColor,
    backgroundColor: Color = CoreTheme.colors.passwordTextInputField.bgColor,
    elevation: Dp = CoreTheme.spacings.passwordTextInputField.elevation,
    shadowColor: Color = CoreTheme.colors.passwordTextInputField.shadowColor
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val visualTransformation = remember(isPasswordVisible) {
        if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    }

    val endIcon = if (isPasswordVisible) {
        Icons.Default.Visibility
    } else {
        Icons.Default.VisibilityOff
    }

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
        endIconVector = if (showPasswordVisibilityToggle) endIcon else null,
        endIconPainter = null,
        endIconSize = endIconSize,
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
        errorTextColor = errorTextColor,
        onEndIconClick = {
            isPasswordVisible = isPasswordVisible.not()
        }
    )
}