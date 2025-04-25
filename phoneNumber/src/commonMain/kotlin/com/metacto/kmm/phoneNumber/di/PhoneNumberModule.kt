package com.metacto.kmm.phoneNumber.di

import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.metacto.kmm.phoneNumber.phoneNumberManger.PhoneNumberManager
import org.koin.dsl.module

val phoneNumberModule = module {
    single<IPhoneNumberManager> {
        PhoneNumberManager(get())
    }
}