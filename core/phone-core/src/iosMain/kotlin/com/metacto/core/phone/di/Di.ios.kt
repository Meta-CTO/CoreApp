package com.metacto.core.phone.di

import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.metadata.init.ComposeResourceMetadataLoader
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add iOS specific dependencies here

    single<MetadataLoader> {
        ComposeResourceMetadataLoader()
    }
}