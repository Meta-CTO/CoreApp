package com.metacto.core.di

import com.metacto.core.CoreConfigs
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreModule(configs: CoreConfigs) = module {
    // Common dependencies can be added here

    includes(platformModule())
    single { configs }
}

internal expect fun platformModule(): Module