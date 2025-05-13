package com.metacto.core.ui.phone

import androidx.compose.runtime.Composable
import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.metadata.init.ComposeResourceMetadataLoader

@Composable
actual fun defaultMetadataLoader(): MetadataLoader {
    return ComposeResourceMetadataLoader()
}