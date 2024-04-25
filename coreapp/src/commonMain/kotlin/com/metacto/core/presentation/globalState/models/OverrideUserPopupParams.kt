package com.metacto.core.presentation.globalState.models

data class OverrideUserPopupParams(
    val onPositiveClick: ((Int?) -> Unit)? = null,
    val onResetClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)