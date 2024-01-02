package com.sampleApp.app.di

import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.sampleApp.app.presentation.app.globalState.AppGlobalState
import com.sampleApp.app.presentation.app.globalState.IAppGlobalState
import org.koin.dsl.module

val appModule = module {
    single<IAppGlobalState> {
        AppGlobalState()
    }

    single<ICoreGlobalState> {
        get<IAppGlobalState>()
    }
}