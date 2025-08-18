package com.metacto.core.files.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun filesModule() = module {
    // Common dependencies can be added here

    includes(platformModule())
}

internal expect fun platformModule(): Module