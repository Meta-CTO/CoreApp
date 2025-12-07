package com.metacto.catalogapp.presentation.sheetSamples.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.base.BaseSheet
import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.inputFields.PrimaryTextInputField
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

internal class FormSheet : BaseSheet<FormSheet.FormSheetViewModel>() {

    @Composable
    override fun Content() {
        val navManager = koinInject<NavManager>()
        val viewModel = rememberViewModel<FormSheetViewModel>()

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacings.spacing24),
            verticalArrangement = Arrangement.spacedBy(spacings.spacing16)
        ) {
            Text(
                text = "Form Sheet Example",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Fill out the form below",
                style = MaterialTheme.typography.bodyMedium
            )

            PrimaryTextInputField(
                text = name,
                label = "Name",
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryTextInputField(
                text = email,
                label = "Email",
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryFilledButton(
                text = "Submit",
                onClick = {
                    navManager.goBackWithResult(
                        source = FormSheet::class.simpleName,
                        result = "Form submitted: Name=$name, Email=$email"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryFilledButton(
                text = "Cancel",
                onClick = {
                    navManager.goBack()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    internal class FormSheetViewModel : BaseViewModel<FormSheetViewModel.State, FormSheetViewModel.Event, FormSheetViewModel.Effect>() {
        data class State(val dummy: Boolean = false) : ViewState
        sealed class Event : ViewEvent
        sealed class Effect : ViewSideEffect

        override fun setInitialState() = State()
        override fun handleEvents(event: Event): Any = Unit
    }
}
