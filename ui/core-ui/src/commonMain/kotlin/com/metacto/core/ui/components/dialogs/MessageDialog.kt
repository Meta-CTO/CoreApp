package com.metacto.core.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.ok
import com.metacto.core.ui.theme.CoreTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String,
    description: String? = null,
    buttonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    bodyTextAlign: TextAlign = CoreTheme.spacings.messageDialog.bodyTextAlign,
    descriptionTextAlign: TextAlign = CoreTheme.spacings.messageDialog.descriptionTextAlign,
    showToolbar: Boolean = CoreTheme.spacings.messageDialog.showToolbar,
    buttonPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.messageDialog.btnPaddingTop),
    bodyTextStyle: TextStyle = CoreTheme.typography.messageDialog.textStyle,
    descriptionTextStyle: TextStyle = CoreTheme.typography.messageDialog.descriptionStyle,
    bodyTextColor: Color = CoreTheme.colors.messageDialog.bodyTextColor,
    descriptionTextColor: Color = CoreTheme.colors.messageDialog.descriptionTextColor,
    btnTextStyle: TextStyle = CoreTheme.typography.messageDialog.btnTextStyle,
    btnBgColor: Color = CoreTheme.colors.messageDialog.btnBgColor,
    btnTextColor: Color = CoreTheme.colors.messageDialog.btnTextColor,
    bodyNoTitlePadding: Dp = CoreTheme.spacings.messageDialog.noTitlePadding,
    bodyTitlePadding: Dp = CoreTheme.spacings.messageDialog.titlePadding,
    descriptionPadding: Dp = CoreTheme.spacings.messageDialog.descriptionPadding
) {
    // Prepare spacings
    val msgSpacing = if (title?.isNotEmpty() == true)
        bodyTitlePadding
    else
        bodyNoTitlePadding

    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = title,
        showToolbar = showToolbar,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
    ) {
        // Container column
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render body text
            Text(
                text = body,
                textAlign = bodyTextAlign,
                color = bodyTextColor,
                style = bodyTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )

            // Render description text
            if (description != null) {
                Text(
                    text = description,
                    textAlign = descriptionTextAlign,
                    color = descriptionTextColor,
                    style = descriptionTextStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = descriptionPadding)
                )
            }

            // Render ok positive button
            PrimaryFilledButton(
                text = buttonText ?: stringResource(Res.string.ok),
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