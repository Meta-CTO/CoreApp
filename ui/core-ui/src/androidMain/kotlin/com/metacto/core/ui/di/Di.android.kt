package com.metacto.core.ui.di

import com.metacto.core.ui.permissions.IPermissionManager
import com.metacto.core.ui.permissions.PermissionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add Android specific dependencies here

    single<IPermissionManager> {
        PermissionManager(androidContext())
    }
}