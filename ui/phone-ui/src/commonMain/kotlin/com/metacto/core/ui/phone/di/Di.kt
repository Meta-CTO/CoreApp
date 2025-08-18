package com.metacto.core.ui.phone.di

import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import org.koin.core.module.Module
import org.koin.dsl.module

fun phoneUIModule() = module {
    // Common dependencies can be added here

    includes(platformModule())

    single {
        PhoneNumberUtil.createInstance(get())
    }
}

internal expect fun platformModule(): Module