package com.metacto.core.di

import com.metacto.core.CoreConfigs
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.eventBroadcaster.EventBroadcaster
import com.metacto.core.language.ILanguageManager
import com.metacto.core.language.LanguageManager
import com.metacto.kmm.sharedpreferences.KmmPreference
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // iOS specific dependencies can be added here

    single {
        val coreConfigs = get<CoreConfigs>()
        RepositoriesFactory(
            coreConfigs = coreConfigs,
            appStorageName = coreConfigs.storageName,
            shouldShowActualErrorMessages = coreConfigs.shouldShowActualErrorMessages,
            errorClass = coreConfigs.errorClass
        )
    }

    single<KmmPreference> {
        get<RepositoriesFactory<*>>().sharedPreference
    }

    single {
        EventBroadcaster
    }

    single<ILanguageManager> {
        LanguageManager(get())
    }
}