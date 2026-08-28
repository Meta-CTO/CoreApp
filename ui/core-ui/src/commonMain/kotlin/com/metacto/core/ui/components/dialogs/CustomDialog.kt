package com.metacto.core.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.ok
import com.metacto.core.ui.theme.CoreTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    showToolbar: Boolean = false,
    title: String? = null,
    showPositiveButton: Boolean = false,
    positiveButtonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    buttonPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.customDialog.btnPaddingTop),
    btnTextStyle: TextStyle = CoreTheme.typography.customDialog.btnTextStyle,
    btnBgColor: Color = CoreTheme.colors.customDialog.btnBgColor,
    btnTextColor: Color = CoreTheme.colors.customDialog.btnTextColor,
    contentPadding: PaddingValues = PaddingValues(CoreTheme.spacings.appDialog.contentPadding),
    content: @Composable () -> Unit
) {
    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = title,
        showToolbar = showToolbar,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
        contentPadding = contentPadding,
    ) {
        // Container column
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render custom content
            content()

            // Render optional positive button
            if (showPositiveButton) {
                PrimaryFilledButton(
                    text = positiveButtonText ?: stringResource(Res.string.ok),
                    textColor = btnTextColor,
                    textStyle = btnTextStyle,
                    backgroundColor = btnBgColor,
                    isSmall = true,
                    onClick = {
                        onPositiveClick?.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(buttonPadding)
                )
            }
        }
    }
}
