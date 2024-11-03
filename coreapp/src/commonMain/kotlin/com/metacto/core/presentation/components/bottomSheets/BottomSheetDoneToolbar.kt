package com.metacto.core.presentation.components.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.components.buttons.PrimaryTextButton
import com.metacto.core.presentation.components.dividers.VerticalDivider
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.PlatformType
import com.metacto.coreApp.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomSheetDoneToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onDoneClick: (() -> Unit)? = null,
    textPadding: PaddingValues = PaddingValues(horizontal = CoreTheme.spacings.bottomSheetToolbar.titlePadding),
    showDivider: Boolean = true,
    platform: PlatformType
) {

    // ini the theme colors  based on the platform
    val colorTheme = if (platform == PlatformType.ANDROID)
        CoreTheme.colors.bottomSheetToolbar
    else
        CoreTheme.colors.iosBottomSheetToolbar

    // Container column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorTheme.toolbarBackgroundColor)
            .then(modifier)
    ) {
        // Toolbar box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CoreTheme.spacings.bottomSheetToolbar.boxPadding)
        ) {
            // Render start icon if required
            if (onDoneClick != null) {
                PrimaryTextButton(
                    text = stringResource(Res.string.action_done),
                    color = colorTheme.doneActionColor,
                    textStyle = CoreTheme.typography.sheetAction,
                    onClick = onDoneClick,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd)
                )
            }

            // Render title if required
            if (title != null) {
                Text(
                    text = title,
                    style = CoreTheme.typography.sheetTitle,
                    color = colorTheme.titleColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(textPadding)
                )
            }
        }

        // Vertical divider if required
        if (showDivider) {
            VerticalDivider()
        }
    }
}