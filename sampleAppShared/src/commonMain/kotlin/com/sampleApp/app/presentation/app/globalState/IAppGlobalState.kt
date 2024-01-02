package com.sampleApp.app.presentation.app.globalState

import androidx.compose.runtime.State
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.sampleApp.app.presentation.app.globalState.models.AppBackgroundType

interface IAppGlobalState : ICoreGlobalState {
    val appBgState: State<AppBackgroundType>

    fun updateAppBackground(type: AppBackgroundType)
}