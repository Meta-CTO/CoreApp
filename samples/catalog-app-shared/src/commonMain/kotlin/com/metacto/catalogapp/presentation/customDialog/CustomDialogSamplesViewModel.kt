package com.metacto.catalogapp.presentation.customDialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.Effect
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.Event
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesContract.State
import com.metacto.catalogapp.presentation.customDialog.components.dialogs.ComplexDialog
import com.metacto.catalogapp.presentation.customDialog.components.dialogs.DialogWithButtonContent
import com.metacto.catalogapp.presentation.customDialog.components.dialogs.DialogWithFormContent
import com.metacto.catalogapp.presentation.customDialog.components.dialogs.DialogWithToolbarContent
import com.metacto.catalogapp.presentation.customDialog.components.dialogs.SimpleDialogContent
import com.metacto.core.ui.globalState.models.CustomPopupParams
import com.metacto.core.ui.globalState.models.MessagePopupParams
import com.metacto.core.ui.globalState.models.SuccessPopupParams

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
                    SimpleDialogContent()
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
                    DialogWithToolbarContent()
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
                    DialogWithButtonContent()
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
                        SuccessPopupParams(
                            title = "Success",
                            body = "Form submitted!\nName: $name\nEmail: $email"
                        )
                    )
                },
                onDismiss = {
                    // Handle dismiss
                },
                content = {
                    DialogWithFormContent(
                        name = name,
                        onNameChange = { name = it },
                        email = email,
                        onEmailChange = { email = it }
                    )
                }
            )
        )
    }

    private fun showComplexDialog() {
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
                    ComplexDialog(
                        onPrimaryClick = {
                            globalState.idle()
                            globalState.messagePopup(
                                MessagePopupParams(
                                    body = "Primary action clicked!"
                                )
                            )
                        },
                        onSecondaryClick = {
                            globalState.idle()
                            globalState.messagePopup(
                                MessagePopupParams(
                                    body = "Secondary action clicked!"
                                )
                            )
                        }
                    )
                }
            )
        )
    }
}
