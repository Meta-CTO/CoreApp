package com.metacto.playground.di

import com.metacto.core.di.coreModule
import com.metacto.core.ui.di.coreUIModule
import com.metacto.kmm.network.errorhandling.NetworkError
import com.metacto.playground.constants.AppEnvironment
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    environment: AppEnvironment
) = initKoin(
    environment = environment,
    appDeclaration = {}
)

fun initKoin(
    environment: AppEnvironment,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        appModule,
        platformModule,
        viewModelsModule,
        domainModule,
        coreModule(environment.coreConfigs, NetworkError::class),
        coreUIModule(environment.coreUIConfigs)
    )
}