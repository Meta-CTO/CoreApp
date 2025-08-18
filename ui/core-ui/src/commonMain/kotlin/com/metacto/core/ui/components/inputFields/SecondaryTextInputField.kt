package com.metacto.core.ui.components.inputFields

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
import com.metacto.core.ui.theme.CoreTheme.colors
import com.metacto.core.ui.theme.CoreTheme.spacings
import com.metacto.core.ui.theme.CoreTheme.shapes
import com.metacto.core.ui.theme.CoreTheme.typography
import kotlin.time.Duration

@Composable
fun SecondaryTextInputField(
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
    minHeight: Dp = spacings.secondaryTextInputField.minHeight,
    error: String? = null,
    textStyle: TextStyle = typography.secondaryTextInputField.textStyle,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconSize: Dp = spacings.secondaryTextInputField.endIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = spacings.secondaryTextInputField.startIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color? = colors.secondaryTextInputField.iconColor,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    shape: RoundedCornerShape = shapes.secondaryTextInputField.shape,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = false,
    requestFocus: Boolean = false,
    requestFocusDelay: Duration = DEFAULT_REQUEST_FOCUS_DELAY,
    backgroundColor: Color = colors.secondaryTextInputField.bgColor,
    focusedBorderColor: Color = colors.secondaryTextInputField.focusedBorderColor,
    unFocusedBorderColor: Color = colors.secondaryTextInputField.unFocusedBorderColor,
    textColor: Color = colors.secondaryTextInputField.textColor,
    placeholderTextStyle: TextStyle = typography.secondaryTextInputField.placeholderTextStyle,
    placeholderTextColor: Color = colors.secondaryTextInputField.placeholderColor,
    labelTextStyle: TextStyle = typography.secondaryTextInputField.labelTextStyle,
    labelTextColor: Color = colors.secondaryTextInputField.labelColor,
    errorTextStyle: TextStyle = typography.secondaryTextInputField.errorTextStyle,
    errorTextColor: Color = colors.secondaryTextInputField.errorColor,
    elevation: Dp = spacings.secondaryTextInputField.elevation,
    shadowColor: Color = colors.secondaryTextInputField.shadowColor,
    floatingLabelSpacing: Dp = spacings.secondaryTextInputField.floatingLabelSpacing,
    contentPadding: PaddingValues = spacings.secondaryTextInputField.contentPadding,
    focusedBorderThickness: Dp = spacings.secondaryTextInputField.focusedBorderThickness,
    unfocusedBorderThickness: Dp = spacings.secondaryTextInputField.unfocusedBorderThickness,
    minWidth: Dp = spacings.secondaryTextInputField.minWidth,
) {
    BaseTextInputField(
        requestFocus = requestFocus,
        requestFocusDelay = requestFocusDelay,
        text = text,
        label = label,
        onValueChange = onValueChange,
        onClick = onClick,
        elevation = elevation,
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
        shadowColor = shadowColor,
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
        floatingLabelSpacing = floatingLabelSpacing,
        contentPadding = contentPadding,
        focusedBorderThickness = focusedBorderThickness,
        unfocusedBorderThickness = unfocusedBorderThickness,
        minLines = minLines,
        minWidth = minWidth
    )
}