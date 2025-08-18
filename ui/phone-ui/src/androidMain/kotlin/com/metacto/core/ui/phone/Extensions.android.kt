package com.metacto.core.ui.phone

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.metadata.source.AssetsMetadataLoader

@Composable
actual fun defaultMetadataLoader(): MetadataLoader {
    return AssetsMetadataLoader(LocalContext.current.applicationContext.assets)
}