package com.metacto.catalogapp.di

import com.metacto.catalogapp.permissions.PermissionDelegateFactory
import com.metacto.catalogapp.utils.FileHandler
import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import org.koin.dsl.module

actual val platformModule = module {
    // Define iOS specific dependencies here

    single<IPermissionDelegateFactory> {
        PermissionDelegateFactory()
    }

    ///TODO THIS FOR DEMO ONLY JUST TO CREATE FILES TO GET PATHS
    single {
        FileHandler()
    }
}