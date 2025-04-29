package com.metacto.core.ui.mediaplayers.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun mediaPlayersModule() = module {
    // Common dependencies can be added here

    includes(platformModule())
}

internal expect fun platformModule(): Module