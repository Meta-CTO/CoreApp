package com.metacto.core.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.inputFields.TertiaryTextInputField
import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.override
import com.metacto.core.ui.resources.override_current_user
import com.metacto.core.ui.resources.override_current_user_message
import com.metacto.core.ui.resources.reset_current_user
import com.metacto.core.ui.theme.CoreTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OverrideUserDialog(
    modifier: Modifier = Modifier,
    onOverrideClick: ((Int?) -> Unit)? = null,
    onResetClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    showToolbar: Boolean = CoreTheme.spacings.overrideUserDialog.showToolbar,
    msgSpacing: Dp = CoreTheme.spacings.overrideUserDialog.msgSpacing,
    bodyTextAlign: TextAlign = CoreTheme.spacings.overrideUserDialog.bodyTextAlign,
    overrideBtnPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.overrideUserDialog.overrideBtnPadding),
    resetBtnPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.overrideUserDialog.resetBtnPadding),
    idTextPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.overrideUserDialog.idTextPadding),
    overriderBtnTextStyle: TextStyle = CoreTheme.typography.overrideUserDialog.overrideTextStyle,
    overriderBtnBgColor: Color = CoreTheme.colors.overrideUserDialog.overrideBtnBgColor,
    overrideBtnTextColor: Color = CoreTheme.colors.overrideUserDialog.overrideBtnTextColor,
    resetBtnTextStyle: TextStyle = CoreTheme.typography.overrideUserDialog.resetTextStyle,
    resetBtnBgColor: Color = CoreTheme.colors.overrideUserDialog.resetBtnBgColor,
    resetBtnTextColor: Color = CoreTheme.colors.overrideUserDialog.resetBtnTextColor,
    bodyTextColor: Color = CoreTheme.colors.overrideUserDialog.bodyTextColor,
    bodyTextStyle: TextStyle = CoreTheme.typography.overrideUserDialog.bodyTextStyle
) {
    val text = remember { mutableStateOf("") }
    // Prepare spacings

    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = stringResource(Res.string.override_current_user),
        showToolbar = showToolbar,
        onDismiss = onDismiss,
        isCancellable = true,
    ) {
        // Container column
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render body text
            Text(
                text = stringResource(Res.string.override_current_user_message),
                textAlign = bodyTextAlign,
                color = bodyTextColor,
                style = bodyTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )


            // Id field
            TertiaryTextInputField(
                modifier = Modifier.fillMaxWidth()
                    .padding(idTextPadding),
                text = text.value,
                requestFocus = true,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    text.value = it
                }
            )

            PrimaryFilledButton(
                text = stringResource(Res.string.override),
                textColor = overrideBtnTextColor,
                textStyle = overriderBtnTextStyle,
                backgroundColor = overriderBtnBgColor,
                isSmall = true,
                isEnabled = text.value.isNotEmpty(),
                onClick = {
                    onOverrideClick?.invoke(text.value.toIntOrNull())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(overrideBtnPadding)
            )

            if (onResetClick != null) {
                PrimaryFilledButton(
                    text = stringResource(Res.string.reset_current_user),
                    textColor = resetBtnTextColor,
                    textStyle = resetBtnTextStyle,
                    backgroundColor = resetBtnBgColor,
                    isSmall = true,
                    onClick = {
                        onResetClick?.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(resetBtnPadding)
                )
            }
        }
    }
}