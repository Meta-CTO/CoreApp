package com.metacto.core.phone.di

import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.metadata.source.AssetsMetadataLoader
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add Android specific dependencies here

    single<MetadataLoader> {
        AssetsMetadataLoader(androidApplication().assets)
    }
}