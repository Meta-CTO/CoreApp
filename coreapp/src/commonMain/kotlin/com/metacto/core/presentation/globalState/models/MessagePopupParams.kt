package com.metacto.core.presentation.globalState.models

data class MessagePopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val body: String = "",
    val description: String? = null,
    val buttonText: String? = null,
    val onPositiveClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)