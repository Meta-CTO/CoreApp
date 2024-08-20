package com.metacto.core.presentation.components.dialogs

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
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun MessageDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String,
    buttonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    bodyTextAlign: TextAlign = CoreTheme.spacings.messageDialog.bodyTextAlign,
    showToolbar: Boolean = CoreTheme.spacings.messageDialog.showToolbar,
    buttonPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.messageDialog.btnPaddingTop),
    bodyTextStyle: TextStyle = CoreTheme.typography.messageDialog.textStyle,
    bodyTextColor: Color = CoreTheme.colors.messageDialog.bodyTextColor,
    btnTextStyle: TextStyle = CoreTheme.typography.messageDialog.btnTextStyle,
    btnBgColor: Color = CoreTheme.colors.messageDialog.btnBgColor,
    btnTextColor: Color = CoreTheme.colors.messageDialog.btnTextColor,
    bodyNoTitlePadding: Dp = CoreTheme.spacings.messageDialog.noTitlePadding,
    bodyTitlePadding: Dp = CoreTheme.spacings.messageDialog.titlePadding

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

            // Render ok positive button
            PrimaryFilledButton(
                text = buttonText ?: stringResource(MR.strings.ok),
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