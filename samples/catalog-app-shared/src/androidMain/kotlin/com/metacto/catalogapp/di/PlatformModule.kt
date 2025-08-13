package com.metacto.catalogapp.di

import com.metacto.catalogapp.loggers.CrashlyticsLogger
import com.metacto.catalogapp.loggers.ICrashLogger
import org.koin.dsl.module

actual val platformModule = module {
    // Define android specific dependencies here
    single<ICrashLogger> { CrashlyticsLogger() }
}