package com.metacto.core.ui.phone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil

@Composable
fun rememberPhoneNumberUtil(): PhoneNumberUtil {
    val metadataLoader = defaultMetadataLoader()
    return remember {
        PhoneNumberUtil.Companion.createInstance(
            metadataLoader = metadataLoader
        )
    }
}

@Composable
expect fun defaultMetadataLoader(): MetadataLoader