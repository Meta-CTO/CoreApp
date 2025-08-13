package com.metacto.catalogapp.di

import com.metacto.catalogapp.constants.AppEnvironment
import com.metacto.catalogapp.loggers.ICrashLogger
import org.koin.core.context.startKoin

fun initKoin(
    environment: AppEnvironment,
    viewsFactory: IViewsFactory,
    crashLogger: () -> ICrashLogger,
) = startKoin {
    modules(
        *getCommonModules(environment).toTypedArray(),
        getPlatformModule(
            viewsFactory = viewsFactory,
            crashLogger = crashLogger,
        )
    )
}