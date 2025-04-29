package com.metacto.core.ui.mediaplayers.di

import org.koin.core.module.Module
import org.koin.dsl.module

val mediaPlayersModule = module {
    includes(platformModule)

    // Common dependencies can be added here
}

internal expect val platformModule: Module