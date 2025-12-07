package com.metacto.core.di

import com.metacto.core.CoreConfigs
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.eventBroadcaster.EventBroadcaster
import com.metacto.core.language.ILanguageManager
import com.metacto.core.language.LanguageManager
import com.metacto.kmm.network.constants.StrapiVersion
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import com.metacto.kmm.network.repos.CoreAppConfigurationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.reflect.KClass

internal actual fun <T : SerializableNetworkError> platformModule(errorClass: KClass<T>) = module {
    // Android specific dependencies can be added here

    single {
        val coreConfigs = get<CoreConfigs>()
        RepositoriesFactory(
            context = androidContext(),
            coreConfigs = coreConfigs,
            appStorageName = coreConfigs.storageName,
            shouldShowActualErrorMessages = coreConfigs.shouldShowActualErrorMessages,
            errorClass = errorClass
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