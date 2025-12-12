package com.metacto.catalogapp.presentation.customDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.Effect
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.Event
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.State
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.globalState.models.CustomPopupParams

class CustomDialogSamplesViewModel : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.ShowSimpleDialog -> showSimpleDialog()
        Event.ShowDialogWithToolbar -> showDialogWithToolbar()
        Event.ShowDialogWithButton -> showDialogWithButton()
        Event.ShowDialogWithForm -> showDialogWithForm()
        Event.ShowComplexDialog -> showComplexDialog()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun showSimpleDialog() {
        globalState.customPopup(
            CustomPopupParams(
                isCancellable = true,
                showToolbar = false,
                onDismiss = {
                    // Handle dismiss
                },
                content = {
                    Text(
                        text = "This is a simple custom dialog with just text content. You can put any composable content here!",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
        )
    }

    private fun showDialogWithToolbar() {
        globalState.customPopup(
            CustomPopupParams(
                isCancellable = true,
                showToolbar = true,
                title = "Custom Dialog Title",
                onDismiss = {
                    // Handle dismiss
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "This dialog has a toolbar with title and close button.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "You can dismiss it by clicking the close button in the toolbar or outside the dialog.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        )
    }

    private fun showDialogWithButton() {
        globalState.customPopup(
            CustomPopupParams(
                isCancellable = true,
                showToolbar = true,
                title = "Confirmation",
                showPositiveButton = true,
                positiveButtonText = "Got it!",
                onPositiveClick = {
                    // Handle positive button click
                },
                onDismiss = {
                    // Handle dismiss
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "This dialog includes an optional positive button at the bottom.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "The button text can be customized, and you can handle its click event.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        )
    }

    private fun showDialogWithForm() {
        var name by mutableStateOf("")
        var email by mutableStateOf("")

        globalState.customPopup(
            CustomPopupParams(
                isCancellable = true,
                showToolbar = true,
                title = "User Information",
                showPositiveButton = true,
                positiveButtonText = "Submit",
                onPositiveClick = {
                    // Handle form submission
                    globalState.successPopup(
                        com.metacto.core.ui.globalState.models.SuccessPopupParams(
                            title = "Success",
                            body = "Form submitted!\nName: $name\nEmail: $email"
                        )
                    )
                },
                onDismiss = {
                    // Handle dismiss
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Enter your details:",
                            style = MaterialTheme.typography.titleMedium
                        )

                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        )
    }

    private fun showComplexDialog() {
        var selectedOption by mutableStateOf("Option 1")

        globalState.customPopup(
            CustomPopupParams(
                isCancellable = true,
                showToolbar = true,
                title = "Advanced Custom Dialog",
                showPositiveButton = false, // We'll add custom buttons in the content
                onDismiss = {
                    // Handle dismiss
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Complex Dialog Example",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "This dialog demonstrates complete control over the content. You can add any UI elements, including custom buttons.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        // Custom buttons in the content
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PrimaryFilledButton(
                                text = "Primary Action",
                                onClick = {
                                    globalState.idle()
                                    globalState.messagePopup(
                                        com.metacto.core.ui.globalState.models.MessagePopupParams(
                                            body = "Primary action clicked!"
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            PrimaryFilledButton(
                                text = "Secondary Action",
                                onClick = {
                                    globalState.idle()
                                    globalState.messagePopup(
                                        com.metacto.core.ui.globalState.models.MessagePopupParams(
                                            body = "Secondary action clicked!"
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            )
        )
    }
}
