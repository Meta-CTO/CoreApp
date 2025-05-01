package com.metacto.core.ui.components.bottomSheets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.ui.extensions.getBottomHandleHeight
import com.metacto.core.ui.extensions.isGesturesNavBarEnabled

@Composable
fun BottomSheetInsetsContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // Prepare nav bars modifier
    val navBarsModifier = if (isGesturesNavBarEnabled().not()) {
        Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    } else {
        Modifier
    }

    // Render outer box that will respect status and nav bars
    Box(
        modifier = modifier
            .then(navBarsModifier)
    ) {
        // Render inner box that will respect ime padding
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.ime)
                .padding(bottom = getBottomHandleHeight())
        ) {
            // Then render content
            content()
        }
    }
}
