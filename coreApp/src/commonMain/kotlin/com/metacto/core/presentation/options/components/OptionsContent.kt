package com.metacto.core.presentation.options.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.options.OptionsList
import com.metacto.core.presentation.options.OptionsContract.Event
import com.metacto.core.presentation.options.OptionsContract.State

@Composable
fun OptionsContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Bottom sheet container
    BottomSheetContainer(
        startIcon = Icons.Default.Close,
        onStartIconClick = {
            onEvent(Event.CloseClicked)
        }
    ) {
        OptionsList(
            modifier = Modifier.fillMaxWidth(),
            options = state.options,
            onItemClick = {
                onEvent(Event.OptionItemClicked(it))
            }
        )
    }
}