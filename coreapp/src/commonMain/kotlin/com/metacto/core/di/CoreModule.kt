package com.metacto.core.di

import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.navigation.NavManager
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.metacto.core.utils.phoneNumber.PhoneNumberManager
import com.metacto.core.utils.remoteConfigs.FirebaseRemoteConfigs
import com.metacto.core.utils.remoteConfigs.IRemoteConfigs
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.auth
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import io.michaelrocks.libphonenumber.kotlin.metadata.defaultMetadataLoader
import org.koin.dsl.module
import com.swensonhe.strapikmm.repos.AuthRepository as StrapiAuthRepository
import com.swensonhe.strapikmm.repos.UserRepository as StrapiUserRepository

fun coreModule(
    environment: CoreEnvironment,
    actionCodeSettings: ActionCodeSettings,
    appStorageName: String
) = module {

    includes(corePlatformModule(appStorageName))
    includes(coreViewModelsModule)

    single {
        environment
    }

    single {
        NavManager()
    }

    single {
        Firebase.auth
    }

    single {
        get<RepositoriesFactory>().strapiService
    }

    single {
        get<RepositoriesFactory>().sharedPreference
    }

    single {
        StrapiUserRepository(get(), get())
    }

    single {
        StrapiAuthRepository(
            authService = get(),
            userRepository = get(),
            sharedPreference = get(),
            actionCodeSettings = actionCodeSettings
        )
    }

    single<IPhoneNumberManager> {
        PhoneNumberManager(get())
    }

    single {
        val metadataLoader = defaultMetadataLoader()
        PhoneNumberUtil.createInstance(
            metadataLoader = metadataLoader
        )
    }

    single<IRemoteConfigs> {
        FirebaseRemoteConfigs(
            kmmPreference = get(),
            firebaseConfigs = get()
        )
    }
}