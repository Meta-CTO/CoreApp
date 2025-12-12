package com.metacto.core.ui.globalState.models

import androidx.compose.runtime.Composable

/**
 * Parameters for showing a custom popup with composable content.
 *
 * @param isCancellable Whether the dialog can be dismissed by clicking outside or pressing back
 * @param showToolbar Whether to show the toolbar with title and close button
 * @param title Optional title text shown in the toolbar (only visible if showToolbar is true)
 * @param showPositiveButton Whether to show a positive action button at the bottom
 * @param positiveButtonText Text for the positive button (defaults to "OK" if not provided)
 * @param onPositiveClick Callback invoked when the positive button is clicked
 * @param onDismiss Callback invoked when the dialog is dismissed
 * @param content Composable lambda containing the custom content to display in the dialog
 */
data class CustomPopupParams(
    val isCancellable: Boolean = true,
    val showToolbar: Boolean = false,
    val title: String? = null,
    val showPositiveButton: Boolean = false,
    val positiveButtonText: String? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
    val content: @Composable () -> Unit
)
