package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun PickerInputField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    error: String? = null,
    placeholder: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    showTrailingIcon: Boolean = true,
    trailingIcon: ImageVector? = Icons.Default.ExpandMore,
    iconTintColor: Color = CoreTheme.colors.pickerInputField.iconColor,
    textStyle: TextStyle = CoreTheme.typography.pickerInputFieldTextStyle,
    textColor: Color = CoreTheme.colors.pickerInputField.textColor,
    isStaticLabel: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    placeholderTextStyle: TextStyle = CoreTheme.typography.pickerInputFieldPlaceholderTextStyle,
    placeholderTextColor: Color = CoreTheme.colors.pickerInputField.placeholderColor,
    labelTextStyle: TextStyle = CoreTheme.typography.pickerInputFieldLabelTextStyle,
    labelTextColor: Color = CoreTheme.colors.pickerInputField.labelColor,
    errorTextStyle: TextStyle = CoreTheme.typography.pickerInputFieldErrorTextStyle,
    errorTextColor: Color = CoreTheme.colors.pickerInputField.errorColor,
    shape: RoundedCornerShape = CoreTheme.shapes.pickerInputFieldShape
) {
    TertiaryTextInputField(
        text = text,
        label = label,
        enabled = false,
        readOnly = true,
        error = error,
        endIconVector = if (showTrailingIcon) trailingIcon else null,
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
        maxLines = maxLines,
        singleLine = singleLine,
        isStaticLabel = isStaticLabel,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable {
                if (enabled) onClick()
            }
            .then(modifier)
    )
}