package com.metacto.core.di

import com.metacto.core.CoreConfigs
import com.metacto.core.domain.repos.ApiErrorHandling
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.eventBroadcaster.EventBroadcaster
import com.metacto.core.language.ILanguageManager
import com.metacto.core.language.LanguageManager
import com.metacto.kmm.network.constants.StrapiVersion
import com.metacto.kmm.network.repos.CoreAppConfigurationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual fun <T : Any> platformModule(errorHandling: ApiErrorHandling<T>) = module {
    // Android specific dependencies can be added here

    single {
        val coreConfigs = get<CoreConfigs>()
        RepositoriesFactory(
            context = androidContext(),
            coreConfigs = coreConfigs,
            appStorageName = coreConfigs.storageName,
            shouldShowActualErrorMessages = coreConfigs.shouldShowActualErrorMessages,
            networkUserAgent = coreConfigs.networkUserAgent,
            errorHandling = errorHandling
        )
    }

    single {
        val coreConfigs = get<CoreConfigs>()
        CoreAppConfigurationRepository(
            applicationContext = androidContext(),
            appConfigurationService = get(),
            sharedPreference = get(),
            appConfigurationExpirationInMinutes = coreConfigs.appConfigurationExpirationInMinutes,
            enforceDefaultDataWrapper = coreConfigs.strapiVersion == StrapiVersion.V5
        )
    }

    single {
        EventBroadcaster
    }

    single<ILanguageManager> {
        LanguageManager(get())
    }
}