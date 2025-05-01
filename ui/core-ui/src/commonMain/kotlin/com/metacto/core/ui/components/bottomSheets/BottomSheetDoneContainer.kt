package com.metacto.core.ui.components.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.PlatformType
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun BottomSheetDoneContainer(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    title: String? = null,
    platform: PlatformType,
    onDoneClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(),
    showToolbarDivider: Boolean = true,
    content: @Composable() (ColumnScope.() -> Unit),
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
            .background(colorTheme.backgroundColor)
            .then(modifier)
    ) {
        // Render toolbar if required
        val showToolbar = title != null || onDoneClick != null

        if (showToolbar) {
            BottomSheetDoneToolbar(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                platform = platform,
                onDoneClick = onDoneClick,
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