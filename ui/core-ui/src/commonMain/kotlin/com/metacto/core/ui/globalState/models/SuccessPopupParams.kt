package com.metacto.core.ui.globalState.models

data class SuccessPopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val buttonText: String? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)