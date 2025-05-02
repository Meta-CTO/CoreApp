package com.metacto.playground.di

import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import com.metacto.playground.permissions.PermissionDelegateFactory
import org.koin.dsl.module

actual val platformModule = module {
    // Define iOS specific dependencies here

    single<IPermissionDelegateFactory> {
        PermissionDelegateFactory()
    }
}