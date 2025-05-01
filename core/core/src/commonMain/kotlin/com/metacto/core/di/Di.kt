package com.metacto.core.di

import com.metacto.core.CoreConfigs
import com.metacto.core.deepLink.DeepLinkManager
import com.metacto.core.deepLink.IDeepLinkManager
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.domain.repos.UploadRepository
import com.metacto.kmm.network.logs.Logger
import com.metacto.kmm.network.repos.CoreLogoutUseCase
import com.metacto.kmm.network.repos.CoreUserRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreModule(configs: CoreConfigs) = module {
    // Common dependencies can be added here

    includes(platformModule())

    single {
        configs
    }

    single {
        CoreLogoutUseCase(get())
    }

    single {
        get<RepositoriesFactory<*>>().httpService
    }

    single {
        get<RepositoriesFactory<*>>().sharedPreference
    }

    single {
        CoreUserRepository(get(), get(), get())
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

    single {
        UploadRepository(get(), get(), get())
    }
}

internal expect fun platformModule(): Module