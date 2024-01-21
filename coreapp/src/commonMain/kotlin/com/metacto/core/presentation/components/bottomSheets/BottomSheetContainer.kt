package com.metacto.core.presentation.components.bottomSheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun BottomSheetContainer(
    modifier: Modifier = Modifier,
    minHeight: Dp = CoreTheme.spacings.minSheetHeight,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    title: String? = null,
    titleStyle: TextStyle = CoreTheme.typography.sheetTitle,
    startIcon: ImageVector? = Icons.Default.Close,
    onStartIconClick: (() -> Unit)? = null,
    endIcon: ImageVector? = null,
    onEndIconClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(),
    showToolbarDivider: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    // Container column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .then(modifier)
    ) {
        // Render toolbar if required
        val showToolbar = title != null || startIcon != null || endIcon != null
        if (showToolbar) {
            BottomSheetToolbar(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                titleStyle = titleStyle,
                startIcon = startIcon,
                onStartIconClick = onStartIconClick,
                endIcon = endIcon,
                onEndIconClick = onEndIconClick,
                showDivider = showToolbarDivider
            )
        }

        // Render content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content(this)
        }
    }
}