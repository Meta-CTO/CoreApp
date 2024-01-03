package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun PickerInputField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    placeholder: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    showTrailingIcon: Boolean = true,
    trailingIcon: ImageVector? = Icons.Default.ExpandMore,
    iconTintColor: Color = CoreTheme.colors.secondary,
) {
    TertiaryTextInputField(
        text = text,
        label = label,
        enabled = false,
        readOnly = true,
        endIconVector = if (showTrailingIcon) trailingIcon else null,
        iconTintColor = iconTintColor,
        onValueChange = {},
        placeholder = placeholder,
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable {
                if (enabled) onClick()
            }
            .then(modifier)
    )
}