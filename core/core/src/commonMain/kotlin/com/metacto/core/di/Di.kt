package com.metacto.core.di

import com.metacto.core.environment.CoreEnvironment
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreModule(
    environment: CoreEnvironment
) = module {
    // Common dependencies can be added here

    includes(platformModule())
    single { environment }
}

internal expect fun platformModule(): Module