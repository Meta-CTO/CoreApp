package com.metacto.core.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun ChoicesDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    btnTextStyle: TextStyle = CoreTheme.typography.choicesDialog.btnTextStyle,
    btnBgColor: Color = CoreTheme.colors.choicesDialog.btnBgColor,
    btnTextColor: Color = CoreTheme.colors.choicesDialog.btnTextColor,
    choices: List<String>,
    showToolbar: Boolean = CoreTheme.spacings.choicesDialog.showToolbar,
    onChoiceSelected: (String, Int) -> Unit,
    onDismiss: (() -> Unit)? = null,
    verticalSpacing: Dp = CoreTheme.spacings.choicesDialog.verticalSpacing
) {
    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = title,
        showToolbar = showToolbar,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
    ) {
        // Render choices
        LazyColumn(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                items = choices
            ) { index, item ->
                PrimaryFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = btnTextStyle,
                    backgroundColor = btnBgColor,
                    textColor = btnTextColor,
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