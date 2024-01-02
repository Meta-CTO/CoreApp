package com.metacto.core.presentation.globalState.models

data class ConfirmationPopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val positiveButtonText: String? = null,
    val negativeButtonText: String? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)