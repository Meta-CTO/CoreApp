package com.metacto.core.ui.di

import com.metacto.core.ui.CoreUIConfigs
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreUIModule(configs: CoreUIConfigs) = module {
    // Common dependencies can be added here

    includes(platformModule())
    single { configs }
}

internal expect fun platformModule(): Module