package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.components.buttons.PrimaryStrokedButton
import com.metacto.core.presentation.components.inputFields.TertiaryTextInputField
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun OverrideUserDialog(
    modifier: Modifier = Modifier,
    onPositiveClick: ((Int?) -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    val text = remember { mutableStateOf("") }
    // Prepare spacings
    val msgSpacing = CoreTheme.spacings.paddingXLarge

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
                text = stringResource(MR.strings.override_current_user_confirmation),
                textAlign = TextAlign.Center,
                color = CoreTheme.colors.secondary,
                style = CoreTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )


            // Id field
            TertiaryTextInputField(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = CoreTheme.spacings.paddingLarge),
                text = text.value,
                requestFocus = true,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    text.value = it
                }
            )

            // Render ok positive button
            PrimaryStrokedButton(
                text = stringResource(MR.strings.confirm),
                isSmall = true,
                isEnabled = text.value.isNotEmpty(),
                onClick = {
                    onPositiveClick?.invoke(text.value.toIntOrNull())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = CoreTheme.spacings.popupSpacingLarge)
            )
        }
    }
}