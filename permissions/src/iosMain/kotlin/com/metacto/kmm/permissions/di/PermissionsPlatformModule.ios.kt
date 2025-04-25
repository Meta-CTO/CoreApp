package com.metacto.kmm.permissions.di

import com.metacto.kmm.permissions.IPermissionManager
import com.metacto.kmm.permissions.PermissionManager
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun providePermissionsPlatformModule(): Module = module {

    single<IPermissionManager> {
        PermissionManager(get())
    }

}