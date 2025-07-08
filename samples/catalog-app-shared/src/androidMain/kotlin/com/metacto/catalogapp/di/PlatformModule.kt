package com.metacto.catalogapp.di

import com.metacto.catalogapp.permissions.PermissionFactory
import com.metacto.core.ui.permissions.helpers.IPermissionFactory
import org.koin.dsl.module

actual val platformModule = module {
    // Define android specific dependencies here
    single<IPermissionFactory> {
        PermissionFactory()
    }
}