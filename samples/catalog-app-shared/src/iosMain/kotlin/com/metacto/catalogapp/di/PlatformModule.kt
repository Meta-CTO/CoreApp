package com.metacto.catalogapp.di

import com.metacto.catalogapp.crash.FirebaseCrashlytics
import com.metacto.catalogapp.permissions.PermissionDelegateFactory
import com.metacto.catalogapp.presentation.app.viewsFactory.IViewsFactory
import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import org.koin.dsl.module

internal fun getPlatformModule(
    viewsFactory: IViewsFactory,
    firebaseCrashlytics: () -> FirebaseCrashlytics,
) = module {
    // Define iOS specific dependencies here
    single<IPermissionDelegateFactory> { PermissionDelegateFactory() }
    single<IViewsFactory> { viewsFactory }
    single<FirebaseCrashlytics> { firebaseCrashlytics() }
}