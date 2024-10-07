package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.utils.extensions.isGesturesNavBarEnabled
import com.metacto.core.utils.extensions.modifyIf

@Composable
fun SafeInsetsColumn(
    modifier: Modifier = Modifier,
    enableSafeInsets: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    // Render outer box that will respect status and nav bars
    Box(
        modifier = modifier
            .modifyIf(enableSafeInsets) {
                statusBarsPadding()
            }
            .modifyIf(enableSafeInsets && isGesturesNavBarEnabled().not()) {
                navigationBarsPadding()
            }
    ) {
        // Render inner column that will respect ime padding
        Column(
            modifier = Modifier
                .modifyIf(enableSafeInsets) { safeDrawingPadding() }
                .modifyIf(enableSafeInsets) { imePadding() }
        ) {
            // Then render content
            content(this)
        }
    }
}
