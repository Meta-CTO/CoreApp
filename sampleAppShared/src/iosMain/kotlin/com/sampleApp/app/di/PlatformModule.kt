package com.sampleApp.app.di

import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import com.sampleApp.app.permissions.PermissionDelegateFactory
import org.koin.dsl.module

actual val platformModule = module {
    // Define iOS specific dependencies here

    single<IPermissionDelegateFactory> {
        PermissionDelegateFactory()
    }
}