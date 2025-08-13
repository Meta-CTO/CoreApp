package com.metacto.catalogapp.di

import com.metacto.catalogapp.loggers.ICrashLogger
import com.metacto.catalogapp.permissions.PermissionDelegateFactory
import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import org.koin.dsl.module

internal fun getPlatformModule(
    viewsFactory: IViewsFactory,
    crashLogger: () -> ICrashLogger,
) = module {
    // Define iOS specific dependencies here
    single<IPermissionDelegateFactory> { PermissionDelegateFactory() }
    single<IViewsFactory> { viewsFactory }
    single<ICrashLogger> { crashLogger() }
}