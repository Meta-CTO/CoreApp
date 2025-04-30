package com.metacto.core.ui.di

import com.metacto.core.ui.permissions.IPermissionManager
import com.metacto.core.ui.permissions.PermissionManager
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add iOS specific dependencies here

    single<IPermissionManager> {
        PermissionManager(
            delegateFactory = get()
        )
    }
}