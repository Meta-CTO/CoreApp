package com.metacto.core.ui.globalState.models

data class SnackBarParams(
    val isVisible: Boolean = true,
    val message: String,
    val type: SnackBarType
) {
    companion object {
        fun hidden() = SnackBarParams(
            isVisible = false,
            message = "",
            type = SnackBarType.SUCCESS
        )
    }
}

enum class SnackBarType {
    ERROR,
    SUCCESS,
}