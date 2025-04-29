package com.metacto.core.files.di

import org.koin.core.module.Module
import org.koin.dsl.module

val filesModule = module {
    includes(platformModule)

    // Common dependencies can be added here
}

internal expect val platformModule: Module