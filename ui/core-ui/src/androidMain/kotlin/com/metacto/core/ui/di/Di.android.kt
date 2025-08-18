package com.metacto.core.ui.di

import com.metacto.core.ui.components.itemPicker.NativeItemPicker
import com.metacto.core.ui.imagePreloader.IPreloader
import com.metacto.core.ui.imagePreloader.Preloader
import com.metacto.core.ui.launchers.IIntentLauncher
import com.metacto.core.ui.launchers.IntentLauncher
import com.metacto.core.ui.media.IMediaManager
import com.metacto.core.ui.media.MediaManager
import com.metacto.core.ui.permissions.IPermissionManager
import com.metacto.core.ui.permissions.PermissionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add Android specific dependencies here

    single<IPermissionManager> {
        PermissionManager(androidContext())
    }

    single<IPreloader> {
        Preloader(
            context = androidContext().applicationContext
        )
    }

    single<IIntentLauncher> {
        IntentLauncher(androidContext(), get())
    }

    single<IMediaManager> {
        MediaManager(androidContext())
    }

    single {
        NativeItemPicker()
    }
}