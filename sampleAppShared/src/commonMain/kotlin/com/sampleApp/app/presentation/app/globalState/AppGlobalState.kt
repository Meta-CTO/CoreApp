package com.sampleApp.app.presentation.app.globalState

import androidx.compose.runtime.mutableStateOf
import com.metacto.core.presentation.globalState.CoreGlobalState
import com.sampleApp.app.presentation.app.globalState.models.AppBackgroundType

class AppGlobalState : CoreGlobalState(), IAppGlobalState {
    override val appBgState = mutableStateOf(AppBackgroundType.PRIMARY)

    override fun updateAppBackground(type: AppBackgroundType) {
        appBgState.value = type
    }
}