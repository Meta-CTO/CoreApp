package com.metacto.core.presentation.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.components.dividers.VerticalDivider
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    startIcon: ImageVector = Icons.Default.ArrowBackIos,
    endIcon: ImageVector = Icons.Default.Close,
    showStartIcon: Boolean = false,
    showEndIcon: Boolean = false,
    startIconTintColor: Color = CoreTheme.colors.secondary,
    endIconTintColor: Color = CoreTheme.colors.secondary,
    titleColor: Color = CoreTheme.colors.secondary,
    titleStyle: TextStyle = CoreTheme.typography.titleBold,
    backgroundColor: Color = Color.Transparent,
    onStartIconClick: () -> Unit = {},
    onEndIconClick: () -> Unit = {},
    showDivider: Boolean = false
) {
    // Container column
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(CoreTheme.spacings.toolbarHeight)
            .background(backgroundColor)
    ) {
        // Container box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = CoreTheme.spacings.paddingMedium)
        ) {
            // show the title if exists
            if (title != null) {
                SingleLineText(
                    modifier = Modifier.align(Alignment.Center),
                    text = title,
                    color = titleColor,
                    textAlign = TextAlign.Center,
                    style = titleStyle
                )
            }

            // draw the start action if available
            if (showStartIcon) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .defaultMinSize(
                            minHeight = CoreTheme.spacings.toolbarIconMinSize,
                            minWidth = CoreTheme.spacings.toolbarIconMinSize
                        )
                        .noRippleClickable(onClick = onStartIconClick)
                ) {
                    Image(
                        imageVector = startIcon,
                        alignment = Alignment.Center,
                        contentDescription = "Toolbar start icon",
                        modifier = Modifier.align(Alignment.CenterStart),
                        colorFilter = ColorFilter.tint(startIconTintColor)
                    )
                }
            }

            // draw the end action if available
            if (showEndIcon) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .defaultMinSize(
                            minHeight = CoreTheme.spacings.toolbarIconMinSize,
                            minWidth = CoreTheme.spacings.toolbarIconMinSize
                        )
                        .noRippleClickable(onClick = onEndIconClick)
                ) {
                    Image(
                        imageVector = endIcon,
                        alignment = Alignment.Center,
                        contentDescription = "Toolbar end icon",
                        modifier = Modifier.align(Alignment.CenterStart),
                        colorFilter = ColorFilter.tint(endIconTintColor)
                    )
                }
            }
        }

        // Render divider if required
        if (showDivider) {
            VerticalDivider()
        }
    }
}
