package com.metacto.core.presentation.components.bottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.components.dividers.VerticalDivider
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun BottomSheetToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleStyle: TextStyle = CoreTheme.typography.sheetTitle,
    startIcon: ImageVector? = null,
    onStartIconClick: (() -> Unit)? = null,
    endIcon: ImageVector? = null,
    onEndIconClick: (() -> Unit)? = null,
    startIconColor: Color = CoreTheme.colors.bottomSheetToolbar.startIconColor,
    endIconColor: Color = CoreTheme.colors.bottomSheetToolbar.endIconColor,
    titleColor: Color = CoreTheme.colors.bottomSheetToolbar.titleColor,
    iconSize: Dp = CoreTheme.spacings.bottomSheetToolbar.iconSize,
    boxPadding: Dp = CoreTheme.spacings.bottomSheetToolbar.boxPadding,
    startIconPadding: Dp = CoreTheme.spacings.bottomSheetToolbar.startIconPadding,
    endIconPadding: Dp = CoreTheme.spacings.bottomSheetToolbar.endIconPadding,
    textPadding: PaddingValues = PaddingValues(horizontal = CoreTheme.spacings.bottomSheetToolbar.titlePadding),
    showDivider: Boolean = true
) {

    // Container column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        // Toolbar box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(boxPadding)
        ) {
            // Render start icon if required
            if (startIcon != null) {
                Image(
                    imageVector = startIcon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(startIconColor),
                    modifier = Modifier
                        .size(iconSize)
                        .padding(startIconPadding)
                        .align(Alignment.CenterStart)
                        .noRippleClickable(onClick = onStartIconClick)
                )
            }

            // Render title if required
            if (title != null) {
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = iconSize)
                        .padding(textPadding)
                )
            }

            // Render end icon if required
            if (endIcon != null) {
                Image(
                    imageVector = endIcon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(endIconColor),
                    modifier = Modifier
                        .size(iconSize)
                        .padding(endIconPadding)
                        .align(Alignment.CenterEnd)
                        .noRippleClickable(onClick = onEndIconClick)
                )
            }
        }

        // Vertical divider if required
        if (showDivider) {
            VerticalDivider()
        }
    }
}