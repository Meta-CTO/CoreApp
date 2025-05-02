package com.metacto.catalogapp.di

import com.metacto.core.ui.globalState.ICoreGlobalState
import com.metacto.catalogapp.presentation.app.globalState.AppGlobalState
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import org.koin.dsl.module

val appModule = module {
    single<IAppGlobalState> {
        AppGlobalState()
    }

    single<ICoreGlobalState> {
        get<IAppGlobalState>()
    }
}