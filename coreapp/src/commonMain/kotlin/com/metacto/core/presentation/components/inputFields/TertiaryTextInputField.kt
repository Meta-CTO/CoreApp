package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.layout.PaddingValues
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
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.presentation.theme.CoreTheme.shapes
import com.metacto.core.presentation.theme.CoreTheme.spacings
import com.metacto.core.presentation.theme.CoreTheme.typography
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
    minLines: Int = 1,
    minHeight: Dp = spacings.tertiaryTextInputField.minHeight,
    error: String? = null,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = spacings.tertiaryTextInputField.endIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = spacings.tertiaryTextInputField.startIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color? = colors.tertiaryTextInputField.iconColor,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    shape: RoundedCornerShape = shapes.tertiaryTextInputField.shape,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = false,
    requestFocus: Boolean = false,
    requestFocusDelay: Duration = DEFAULT_REQUEST_FOCUS_DELAY,
    textStyle: TextStyle = typography.tertiaryTextInputField.textStyle,
    textColor: Color = colors.tertiaryTextInputField.textColor,
    placeholderTextStyle: TextStyle = typography.tertiaryTextInputField.placeholderTextStyle,
    placeholderTextColor: Color = colors.tertiaryTextInputField.placeholderColor,
    labelTextStyle: TextStyle = typography.tertiaryTextInputField.labelTextStyle,
    labelTextColor: Color = colors.tertiaryTextInputField.labelColor,
    errorTextStyle: TextStyle = typography.tertiaryTextInputField.errorTextStyle,
    errorTextColor: Color = colors.tertiaryTextInputField.errorColor,
    focusedBorderColor: Color = colors.tertiaryTextInputField.focusedBorderColor,
    unFocusedBorderColor: Color = colors.tertiaryTextInputField.unFocusedBorderColor,
    backgroundColor: Color = colors.tertiaryTextInputField.bgColor,
    elevation: Dp = spacings.tertiaryTextInputField.elevation,
    shadowColor: Color = colors.secondaryTextInputField.shadowColor,
    floatingLabelSpacing: Dp = spacings.tertiaryTextInputField.floatingLabelSpacing,
    contentPadding: PaddingValues = spacings.tertiaryTextInputField.contentPadding,
    focusedBorderThickness: Dp = spacings.tertiaryTextInputField.focusedBorderThickness,
    unfocusedBorderThickness: Dp = spacings.tertiaryTextInputField.unfocusedBorderThickness,
    minWidth: Dp = spacings.tertiaryTextInputField.minWidth,
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
        errorTextColor = errorTextColor,
        floatingLabelSpacing = floatingLabelSpacing,
        contentPadding = contentPadding,
        focusedBorderThickness = focusedBorderThickness,
        unfocusedBorderThickness = unfocusedBorderThickness,
        minLines = minLines,
        minWidth = minWidth
    )
}