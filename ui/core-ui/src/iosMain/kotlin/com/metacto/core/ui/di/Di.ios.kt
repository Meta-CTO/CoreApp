package com.metacto.core.ui.di

import coil3.PlatformContext
import com.metacto.core.ui.components.itemPicker.NativeItemPicker
import com.metacto.core.ui.imagePreloader.IPreloader
import com.metacto.core.ui.imagePreloader.Preloader
import com.metacto.core.ui.launchers.IIntentLauncher
import com.metacto.core.ui.launchers.IntentLauncher
import com.metacto.core.ui.media.IMediaManager
import com.metacto.core.ui.media.MediaManager
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

    single<IPreloader> {
        Preloader(
            context = PlatformContext.INSTANCE
        )
    }

    single<IIntentLauncher> {
        IntentLauncher()
    }

    single<IMediaManager> {
        MediaManager()
    }

    single {
        NativeItemPicker(get())
    }
}