package com.metacto.catalogapp.di

import com.metacto.catalogapp.constants.AppEnvironment
import com.metacto.catalogapp.crash.CrashLogger
import com.metacto.catalogapp.presentation.app.globalState.AppGlobalState
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.ui.globalState.ICoreGlobalState
import com.metacto.kmm.firebase.remoteconfig.FirebaseRemoteConfigsProvider
import com.metacto.kmm.remoteconfig.common.RemoteConfigProvider
import org.koin.dsl.module

fun appModule(environment: AppEnvironment) = module {
    single { environment }
    single<IAppGlobalState> { AppGlobalState() }
    single<ICoreGlobalState> { get<IAppGlobalState>() }
    single<RemoteConfigProvider> { FirebaseRemoteConfigsProvider(get()) }
    single { get<RepositoriesFactory<*>>().sharedPreference }
    single { CrashLogger() }
}