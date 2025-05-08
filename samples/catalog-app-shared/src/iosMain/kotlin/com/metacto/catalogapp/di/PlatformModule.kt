package com.metacto.catalogapp.di

import com.metacto.catalogapp.permissions.PermissionDelegateFactory
import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import org.koin.dsl.module

actual val platformModule = module {
    // Define iOS specific dependencies here

    single<IPermissionDelegateFactory> {
        PermissionDelegateFactory()
    }

}