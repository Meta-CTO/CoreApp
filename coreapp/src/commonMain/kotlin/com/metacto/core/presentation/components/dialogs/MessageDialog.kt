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
import com.metacto.coreApp.MR
import com.metacto.core.presentation.components.buttons.PrimaryStrokedButton
import com.metacto.core.presentation.theme.CoreTheme
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
    buttonPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.popupSpacingLarge),
    bodyTextStyle: TextStyle = CoreTheme.typography.messageDialogBodyTextStyle,
    bodyTextColor: Color = CoreTheme.colors.messageDialogBodyTextColor,
    bodyNoTitlePadding: Dp = CoreTheme.spacings.messageDialogBodyNoTitlePadding,
    bodyTitlePadding: Dp = CoreTheme.spacings.messageDialogBodyTitlePadding

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
        showToolbar = true,
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
                textAlign = TextAlign.Center,
                color = bodyTextColor,
                style = bodyTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )

            // Render ok positive button
            PrimaryStrokedButton(
                text = buttonText ?: stringResource(MR.strings.ok),
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