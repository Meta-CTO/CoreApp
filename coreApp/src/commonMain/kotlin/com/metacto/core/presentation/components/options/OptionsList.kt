package com.metacto.core.presentation.components.options

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.options.models.OptionUIModel

@Composable
fun OptionsList(
    modifier: Modifier = Modifier,
    options: List<OptionUIModel>,
    onItemClick: (OptionUIModel) -> Unit
) {
    // Container column
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(options) { item ->
            OptionItem(
                option = item,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onItemClick.invoke(item)
                }
            )
        }
    }
}