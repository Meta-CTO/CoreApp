package com.metacto.core.di

import com.metacto.core.CoreConfigs
import com.metacto.core.deepLink.DeepLinkManager
import com.metacto.core.deepLink.IDeepLinkManager
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.domain.repos.UploadRepository
import com.metacto.kmm.logger.Logger
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import com.metacto.kmm.network.repos.CoreAppConfigurationRepository
import com.metacto.kmm.network.repos.CoreFirebaseAuthRepository
import com.metacto.kmm.network.repos.CoreLogoutUseCase
import com.metacto.kmm.network.repos.CoreUploaderRepository
import com.metacto.kmm.network.repos.CoreUserRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.reflect.KClass

fun <T : SerializableNetworkError> coreModule(configs: CoreConfigs, errorClass: KClass<T>) = module {
    // Common dependencies can be added here

    includes(platformModule(errorClass))

    single {
        configs
    }

    single {
        get<RepositoriesFactory<*>>().httpService
    }

    single {
        get<RepositoriesFactory<*>>().sharedPreference
    }

    single {
        CoreAppConfigurationRepository(
            applicationContext = get(),
            appConfigurationService = get(),
            sharedPreference = get(),
            appConfigurationExpirationInMinutes = configs.appConfigurationExpirationInMinutes
        )
    }

    single {
        CoreUserRepository(get(), get(), get())
    }

    single {
        CoreFirebaseAuthRepository(get(), get(), get())
    }

    single {
        CoreUploaderRepository(get())
    }

    single {
        UploadRepository(get(), get(), get())
    }

    single {
        CoreLogoutUseCase(get())
    }

    single {
        Logger("")
    }

    single<IDeepLinkManager> {
        DeepLinkManager(
            appLogger = get(),
            parsers = configs.deepLinkParsers
        )
    }
}

internal expect fun <T : SerializableNetworkError> platformModule(errorClass: KClass<T>): Module