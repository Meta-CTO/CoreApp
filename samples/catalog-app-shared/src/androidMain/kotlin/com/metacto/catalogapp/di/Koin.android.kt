package com.metacto.catalogapp.di

import com.metacto.catalogapp.constants.AppEnvironment
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    environment: AppEnvironment,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        *getCommonModules(environment).toTypedArray(),
        getPlatformModule()
    )
}