package com.metacto.core.ui.sample.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun sampleModule() = module {
    // Common dependencies can be added here

    includes(platformModule())
}

internal expect fun platformModule(): Module