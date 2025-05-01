package com.metacto.core.ui.globalState.models

data class OverrideUserPopupParams(
    val onPositiveClick: ((Int?) -> Unit)? = null,
    val onResetClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)