package com.metacto.core.sample.di

import org.koin.core.module.Module
import org.koin.dsl.module

val sampleModule = module {
    includes(platformModule)

    // Common dependencies can be added here
}

internal expect val platformModule: Module