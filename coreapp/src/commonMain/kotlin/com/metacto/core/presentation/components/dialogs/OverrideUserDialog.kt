package com.metacto.core.presentation.components.dialogs

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
import com.metacto.core.presentation.components.buttons.PrimaryStrokedButton
import com.metacto.core.presentation.components.inputFields.TertiaryTextInputField
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun OverrideUserDialog(
    modifier: Modifier = Modifier,
    onOverrideClick: ((Int?) -> Unit)? = null,
    onResetClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    msgSpacing: Dp = CoreTheme.spacings.overrideUserDialogMsgSpacing,
    overrideBtnPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.overrideUserDialogOverrideBtnPadding),
    resetBtnPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.overrideUserDialogResetBtnPadding),
    idTextPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.overrideUserDialogIdTextPadding),
    bodyTextColor: Color = CoreTheme.colors.overrideUserDialogBodyTextColor,
    bodyTextStyle: TextStyle = CoreTheme.typography.bodyLarge

) {
    val text = remember { mutableStateOf("") }
    // Prepare spacings

    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = stringResource(MR.strings.override_current_user),
        showToolbar = true,
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
                text = stringResource(MR.strings.override_current_user_message),
                textAlign = TextAlign.Center,
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

            PrimaryStrokedButton(
                text = stringResource(MR.strings.override),
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
                PrimaryStrokedButton(
                    text = stringResource(MR.strings.reset_current_user),
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