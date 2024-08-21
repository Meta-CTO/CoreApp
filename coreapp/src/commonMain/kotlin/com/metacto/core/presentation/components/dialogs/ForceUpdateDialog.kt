package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.buttons.PrimaryTextButton
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.asCommon
import com.metacto.core.utils.painterResource
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ForceUpdateDialog(
    modifier: Modifier = Modifier,
    isRequired: Boolean = true,
    body: String? = null,
    title: String? = null,
    updateButtonText: String? = null,
    skipUpdateButtonText: String? = null,
    onUpdateClick: (() -> Unit)? = null,
    onSkipUpdateClicked: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    image: CommonImageResource = MR.images.ic_upgrade.asCommon(),
    bodyTextStyle: TextStyle = CoreTheme.typography.forceUpdateDialog.textStyle,
    bodyTextColor: Color = CoreTheme.colors.forceUpdateDialog.bodyTextColor,
    textPadding: Dp = CoreTheme.spacings.forceUpdateDialog.textPadding,
    dialogPadding: Dp = CoreTheme.spacings.forceUpdateDialog.dialogPadding,
    dialogSpacings: Dp = CoreTheme.spacings.forceUpdateDialog.dialogSpacings,
    buttonsPadding: PaddingValues = PaddingValues(
        horizontal = CoreTheme.spacings.forceUpdateDialog.horizontalButtonsPadding
    )
) {
    // Prepare spacings
    val msgSpacing = if (title?.isNotEmpty() == true) CoreTheme.spacings.paddingXLarge else 0.dp

    // Render app dialog
    AppDialog(
        modifier = modifier,
        showToolbar = title.orEmpty().isNotEmpty(),
        title = title,
        onDismiss = onDismiss,
        isCancellable = isRequired.not(),
    ) {
        // Container column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dialogSpacings),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dialogPadding)
        ) {

            // top image
            Image(
                painter = painterResource(image),
                contentDescription = "force update",
                modifier = Modifier
                    .size(CoreTheme.spacings.forceUpdateDialog.imageSize)
                    .padding(top = msgSpacing)
            )

            // Render body text
            Text(
                text = body.orEmpty(),
                textAlign = TextAlign.Center,
                color = bodyTextColor,
                style = bodyTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = textPadding)
            )

            // Render buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(dialogSpacings),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(buttonsPadding)
            ) {
                // Positive button
                PrimaryFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle =  CoreTheme.typography.forceUpdateDialog.positiveBtnTextStyle,
                    text = updateButtonText ?: stringResource(MR.strings.confirm),
                    isSmall = true,
                    onClick = {
                        onUpdateClick?.invoke()
                    }
                )

                // show the negative button if update is not required
                if (isRequired.not()) {
                    // Negative button
                    PrimaryTextButton(
                        modifier = Modifier.wrapContentSize(),
                        textStyle =  CoreTheme.typography.forceUpdateDialog.negativeBtnTextStyle,
                        text = skipUpdateButtonText ?: stringResource(MR.strings.skip_update_button),
                        onClick = {
                            onSkipUpdateClicked?.invoke()
                        }
                    )
                }
            }
        }
    }
}