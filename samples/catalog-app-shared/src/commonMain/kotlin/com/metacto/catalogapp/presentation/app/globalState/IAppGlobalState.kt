package com.metacto.catalogapp.presentation.app.globalState

import com.metacto.core.ui.globalState.ICoreGlobalState

interface IAppGlobalState : ICoreGlobalState {
    fun showSuccess(message: String)
    fun showError(message: String)
}