package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.coreApp.MR
import com.metacto.core.presentation.components.buttons.PrimaryStrokedButton
import com.metacto.core.presentation.theme.CoreTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SuccessDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String? = null,
    buttonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    iconSize: Dp = CoreTheme.spacings.successDialog,
    bodyPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.successDialogBodyPaddingTop),
    buttonPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.successDialogBtnPaddingTop),
    bodyTextStyle: TextStyle = CoreTheme.typography.successDialogBodyTextStyle
) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render Image
            Image(
                imageVector = Icons.Default.TaskAlt,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(CoreTheme.colors.success),
                modifier = Modifier.size(iconSize)
            )

            // Render body text
            Text(
                text = body.orEmpty(),
                textAlign = TextAlign.Center,
                color = CoreTheme.colors.secondary,
                style = bodyTextStyle,
                modifier = Modifier.padding(bodyPadding)
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