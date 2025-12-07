package com.metacto.catalogapp.presentation.sheetSamples.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.base.BaseSheet
import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

internal class SimpleContentSheet : BaseSheet<SimpleContentSheet.SimpleSheetViewModel>() {

    @Composable
    override fun Content() {
        val navManager = koinInject<NavManager>()
        val viewModel = rememberViewModel<SimpleSheetViewModel>()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacings.spacing24),
            verticalArrangement = Arrangement.spacedBy(spacings.spacing16)
        ) {
            Text(
                text = "Simple Content Sheet",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "This is a simple sheet with some text content. You can add any composable content here.",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Sheets are useful for:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "• Showing additional information\n• Quick forms or inputs\n• Action menus\n• Confirmation dialogs",
                style = MaterialTheme.typography.bodyMedium
            )

            PrimaryFilledButton(
                text = "Close Sheet",
                onClick = {
                    navManager.goBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacings.spacing16)
            )
        }
    }

    internal class SimpleSheetViewModel : BaseViewModel<SimpleSheetViewModel.State, SimpleSheetViewModel.Event, SimpleSheetViewModel.Effect>() {
        data class State(val dummy: Boolean = false) : ViewState
        sealed class Event : ViewEvent
        sealed class Effect : ViewSideEffect

        override fun setInitialState() = State()
        override fun handleEvents(event: Event): Any = Unit
    }
}
