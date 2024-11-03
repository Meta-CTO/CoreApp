package com.metacto.core.presentation.itemPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
internal actual fun ItemPicker(
    modifier: Modifier,
    items: List<PickerItem>,
    selectedItem: PickerItem?,
    onItemSelected: ((PickerItem) -> Unit)?,
    onDismissRequested: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(colors.sheetBackground)
            .noRippleClickable(onClick = onDismissRequested)
    ) {
        Text(
            text = "Item picker is not supported in Android.\nUse ItemPickerSheet.\nTap to dismiss",
            textAlign = TextAlign.Center
        )
    }
}