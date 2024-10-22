package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.metacto.coreApp.resources.*
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.buttons.PrimaryStrokedButton
import com.metacto.core.presentation.theme.CoreTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onNegativeClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    bodyTextAlign: TextAlign = CoreTheme.spacings.confirmationDialog.bodyTextAlign,
    showToolbar: Boolean = CoreTheme.spacings.confirmationDialog.showToolbar,
    positiveBtnTextStyle: TextStyle = CoreTheme.typography.confirmationDialog.positiveBtnTextStyle,
    positiveBtnTextColor: Color = CoreTheme.colors.confirmationDialog.positiveBtnTextColor,
    positiveBtnBgColor: Color = CoreTheme.colors.confirmationDialog.positiveBtnBgColor,
    negativeBtnTextStyle: TextStyle = CoreTheme.typography.confirmationDialog.negativeBtnTextStyle,
    negativeBtnTextColor: Color = CoreTheme.colors.confirmationDialog.negativeBtnTextColor,
    negativeBtnBgColor: Color = CoreTheme.colors.confirmationDialog.negativeBtnBgColor,
    bodyTextStyle: TextStyle = CoreTheme.typography.confirmationDialog.textStyle,
    bodyTextColor: Color = CoreTheme.colors.confirmationDialog.bodyTextColor,
    noTitlePadding: Dp = CoreTheme.spacings.confirmationDialog.noTitlePadding,
    titlePadding: Dp = CoreTheme.spacings.confirmationDialog.titlePadding,
    buttonsSpacings: Dp = CoreTheme.spacings.confirmationDialog.buttonsSpacings,
    buttonsPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.confirmationDialog.buttonsTopPadding)
) {
    // Prepare spacings
    val msgSpacing = if (title?.isNotEmpty() == true)
        titlePadding
    else
        noTitlePadding

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
                text = body.orEmpty(),
                textAlign = bodyTextAlign,
                color = bodyTextColor,
                style = bodyTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )

            // Render buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonsSpacings),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(buttonsPadding)
            ) {
                // Negative button
                PrimaryStrokedButton(
                    text = negativeButtonText ?: stringResource(Res.string.cancel),
                    textStyle = negativeBtnTextStyle,
                    textColor = negativeBtnTextColor,
                    backgroundColor = negativeBtnBgColor,
                    isSmall = true,
                    onClick = {
                        onNegativeClick?.invoke()
                    },
                    modifier = Modifier.weight(1f)
                )

                // Positive button
                PrimaryFilledButton(
                    text = positiveButtonText ?: stringResource(Res.string.confirm),
                    textStyle = positiveBtnTextStyle,
                    textColor = positiveBtnTextColor,
                    backgroundColor = positiveBtnBgColor,
                    isSmall = true,
                    onClick = {
                        onPositiveClick?.invoke()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
