package com.metacto.catalogapp.presentation.sheetSamples.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

internal class ListSheet : BaseSheet<ListSheet.ListSheetViewModel>() {

    @Composable
    override fun Content() {
        val navManager = koinInject<NavManager>()
        val viewModel = rememberViewModel<ListSheetViewModel>()

        val items = listOf(
            "Option 1" to "First option description",
            "Option 2" to "Second option description",
            "Option 3" to "Third option description",
            "Option 4" to "Fourth option description",
            "Option 5" to "Fifth option description"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacings.spacing24)
        ) {
            Text(
                text = "Select an Option",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = spacings.spacing16)
            )

            items.forEachIndexed { index, (title, description) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navManager.goBackWithResult(
                                source = ListSheet::class.simpleName,
                                result = "Selected: $title"
                            )
                        }
                        .padding(vertical = spacings.spacing12),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (index < items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }

    internal class ListSheetViewModel : BaseViewModel<ListSheetViewModel.State, ListSheetViewModel.Event, ListSheetViewModel.Effect>() {
        data class State(val dummy: Boolean = false) : ViewState
        sealed class Event : ViewEvent
        sealed class Effect : ViewSideEffect

        override fun setInitialState() = State()
        override fun handleEvents(event: Event): Any = Unit
    }
}
