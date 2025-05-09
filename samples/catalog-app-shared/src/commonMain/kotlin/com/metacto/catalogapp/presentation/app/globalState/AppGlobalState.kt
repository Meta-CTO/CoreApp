package com.metacto.catalogapp.presentation.app.globalState

import com.metacto.core.ui.globalState.CoreGlobalState
import com.metacto.core.ui.globalState.models.SnackBarParams
import com.metacto.core.ui.globalState.models.SnackBarType

class AppGlobalState : CoreGlobalState(), IAppGlobalState {
    override fun showSuccess(message: String) {
        snackBar(
            SnackBarParams(
                message = message,
                type = SnackBarType.SUCCESS,
            )
        )
    }

    override fun showError(message: String) {
        snackBar(
            SnackBarParams(
                message = message,
                type = SnackBarType.ERROR,
            )
        )
    }
}