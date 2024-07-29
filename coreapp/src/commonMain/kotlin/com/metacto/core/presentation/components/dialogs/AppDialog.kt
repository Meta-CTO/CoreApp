package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    showToolbar: Boolean = false,
    title: String? = null,
    isCancellable: Boolean = true,
    onDismiss: (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(CoreTheme.spacings.appDialogContentPadding),
    containerPadding : PaddingValues = PaddingValues(CoreTheme.spacings.popupPadding),
    content: @Composable () -> Unit
) {
    // Render dialog
    Dialog(
        onDismissRequest = {
            if (isCancellable) {
                onDismiss?.invoke()
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        // Container column
        Column(
            modifier = modifier
                .padding(containerPadding)
                .clip(CoreTheme.shapes.xLarge)
                .background(CoreTheme.colors.background)
        ) {
            // Render toolbar if required
            if (showToolbar) {
                DialogToolbar(
                    title = title,
                    showClose = isCancellable,
                    onCloseClicked = {
                        onDismiss?.invoke()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Render content
            Box(
                modifier = Modifier.padding(padding)
            ) {
                content()
            }
        }
    }
}