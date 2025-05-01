package com.metacto.core.ui.components.dialogs

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.ok
import com.metacto.core.ui.theme.CoreTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun SuccessDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String? = null,
    buttonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    bodyTextAlign: TextAlign = CoreTheme.spacings.successDialog.bodyTextAlign,
    showToolbar: Boolean = CoreTheme.spacings.successDialog.showToolbar,
    iconSize: Dp = CoreTheme.spacings.successDialog.iconSize,
    btnBgColor: Color = CoreTheme.colors.successDialog.btnBgColor,
    btnTextColor: Color = CoreTheme.colors.successDialog.btnTextColor,
    btnTextStyle: TextStyle = CoreTheme.typography.successDialog.btnTextStyle,
    bodyPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.successDialog.bodyPaddingTop),
    buttonPadding: PaddingValues = PaddingValues(top = CoreTheme.spacings.successDialog.btnPaddingTop),
    bodyTextStyle: TextStyle = CoreTheme.typography.successDialog.bodyTextStyle
) {
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
                textAlign = bodyTextAlign,
                color = CoreTheme.colors.secondary,
                style = bodyTextStyle,
                modifier = Modifier.padding(bodyPadding)
            )

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