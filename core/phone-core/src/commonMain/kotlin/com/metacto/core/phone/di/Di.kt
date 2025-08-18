package com.metacto.core.phone.di

import com.metacto.core.phone.IPhoneNumberManager
import com.metacto.core.phone.PhoneNumberManager
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import org.koin.core.module.Module
import org.koin.dsl.module

fun phoneCoreModule() = module {
    // Common dependencies can be added here

    includes(platformModule())

    single<IPhoneNumberManager> {
        PhoneNumberManager(get())
    }

    single {
        PhoneNumberUtil.createInstance(get())
    }
}

internal expect fun platformModule(): Module