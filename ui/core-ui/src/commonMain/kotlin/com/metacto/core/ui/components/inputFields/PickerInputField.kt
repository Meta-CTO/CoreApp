package com.metacto.core.ui.components.inputFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme.colors
import com.metacto.core.ui.theme.CoreTheme.shapes
import com.metacto.core.ui.theme.CoreTheme.spacings
import com.metacto.core.ui.theme.CoreTheme.typography

@Composable
fun PickerInputField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    error: String? = null,
    placeholder: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    endIconVector: ImageVector? = Icons.Default.ExpandMore,
    endIconPainter: Painter? = null,
    endIconSize: Dp = spacings.pickerInputField.endIconSize,
    onEndIconClick: (() -> Unit)? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    startIconSize: Dp = spacings.pickerInputField.startIconSize,
    onStartIconClick: (() -> Unit)? = null,
    iconTintColor: Color? = colors.pickerInputField.iconColor,
    textStyle: TextStyle = typography.pickerInputField.textStyle,
    textColor: Color = colors.pickerInputField.textColor,
    isStaticLabel: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    singleLine: Boolean = false,
    placeholderTextStyle: TextStyle = typography.pickerInputField.placeholderTextStyle,
    placeholderTextColor: Color = colors.pickerInputField.placeholderColor,
    labelTextStyle: TextStyle = typography.pickerInputField.labelTextStyle,
    labelTextColor: Color = colors.pickerInputField.labelColor,
    errorTextStyle: TextStyle = typography.pickerInputField.errorTextStyle,
    errorTextColor: Color = colors.pickerInputField.errorColor,
    shape: RoundedCornerShape = shapes.pickerInputField.shape,
    floatingLabelSpacing: Dp = spacings.pickerInputField.floatingLabelSpacing,
    contentPadding: PaddingValues = spacings.pickerInputField.contentPadding,
    focusedBorderThickness: Dp = spacings.pickerInputField.focusedBorderThickness,
    unfocusedBorderThickness: Dp = spacings.pickerInputField.unfocusedBorderThickness,
    minWidth: Dp = spacings.pickerInputField.minWidth,
) {
    TertiaryTextInputField(
        text = text,
        label = label,
        enabled = enabled,
        readOnly = true,
        error = error,
        startIconVector = startIconVector,
        startIconPainter = startIconPainter,
        startIconSize = startIconSize,
        onStartIconClick = onStartIconClick,
        endIconVector = endIconVector,
        endIconPainter = endIconPainter,
        endIconSize = endIconSize,
        onEndIconClick = onEndIconClick,
        iconTintColor = iconTintColor,
        onValueChange = {},
        placeholder = placeholder,
        textStyle = textStyle,
        textColor = textColor,
        placeholderTextStyle = placeholderTextStyle,
        placeholderTextColor = placeholderTextColor,
        labelTextStyle = labelTextStyle,
        labelTextColor = labelTextColor,
        errorTextStyle = errorTextStyle,
        errorTextColor = errorTextColor,
        onClick = onClick,
        maxLines = maxLines,
        singleLine = singleLine,
        isStaticLabel = isStaticLabel,
        shape = shape,
        floatingLabelSpacing = floatingLabelSpacing,
        contentPadding = contentPadding,
        focusedBorderThickness = focusedBorderThickness,
        unfocusedBorderThickness = unfocusedBorderThickness,
        minLines = minLines,
        minWidth = minWidth,
        modifier = modifier
    )
}