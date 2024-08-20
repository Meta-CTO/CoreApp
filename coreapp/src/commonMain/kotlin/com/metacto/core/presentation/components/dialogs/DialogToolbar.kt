package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun DialogToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showClose: Boolean = false,
    onCloseClicked: () -> Unit = {},
    closeSize: Dp = CoreTheme.spacings.dialogToolbar.closeSize,
    closeColor: Color = CoreTheme.colors.dialogToolbar.closeColor,
    textColor: Color = CoreTheme.colors.dialogToolbar.textColor,
    textStyle: TextStyle = CoreTheme.typography.dialogToolbar.textStyle,
    paddingHorizontal: PaddingValues = PaddingValues(horizontal = CoreTheme.spacings.dialogToolbar.paddingHorizontal),
    paddingVertical: PaddingValues = PaddingValues(
        top = CoreTheme.spacings.dialogToolbar.paddingTop,
        bottom = CoreTheme.spacings.dialogToolbar.paddingBottom
    )
) {
    // Toolbar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingHorizontal)
            .padding(paddingVertical)
    ) {
        // Render close icon if required
        if (showClose) {
            Image(
                imageVector = Icons.Filled.Close,
                colorFilter = ColorFilter.tint(closeColor),
                contentDescription = null,
                modifier = Modifier
                    .size(closeSize)
                    .noRippleClickable(onClick = onCloseClicked)
                    .align(Alignment.CenterStart)
            )
        }

        // Render title
        Text(
            text = title.orEmpty(),
            textAlign = TextAlign.Center,
            color = textColor,
            style = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = closeSize)
                .align(Alignment.Center)
        )
    }
}