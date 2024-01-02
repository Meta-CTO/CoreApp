package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun ChoicesDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    choices: List<String>,
    onChoiceSelected: (String, Int) -> Unit,
    onDismiss: (() -> Unit)? = null
) {
    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = title,
        showToolbar = true,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
    ) {
        // Render choices
        LazyColumn(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(CoreTheme.spacings.paddingSmall),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                items = choices
            ) { index, item ->
                PrimaryFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = item,
                    isSmall = true,
                    onClick = {
                        onChoiceSelected(item, index)
                    }
                )
            }
        }
    }
}