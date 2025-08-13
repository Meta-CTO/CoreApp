package com.metacto.catalogapp.di

import com.metacto.catalogapp.loggers.CrashlyticsLogger
import com.metacto.catalogapp.loggers.ICrashLogger
import org.koin.dsl.module

internal fun getPlatformModule() = module {
    // Define platform specific dependencies here
    single<ICrashLogger> { CrashlyticsLogger() }
}