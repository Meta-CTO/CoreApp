package com.metacto.catalogapp.di

import com.metacto.catalogapp.crash.FirebaseCrashlytics
import com.metacto.catalogapp.crash.FirebaseCrashlyticsImpl
import org.koin.dsl.module

internal fun getPlatformModule() = module {
    // Define platform specific dependencies here
    single<FirebaseCrashlytics> { FirebaseCrashlyticsImpl() }
}