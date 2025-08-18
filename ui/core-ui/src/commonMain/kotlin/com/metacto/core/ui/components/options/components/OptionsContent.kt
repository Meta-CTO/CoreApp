package com.metacto.core.ui.components.options.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.ui.components.bottomSheets.BottomSheetContainer
import com.metacto.core.ui.components.options.OptionsContract.Event
import com.metacto.core.ui.components.options.OptionsContract.State
import com.metacto.core.ui.components.options.OptionsList

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