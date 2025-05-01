package com.metacto.core.di

import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.domain.repos.UploadRepository
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.date.DateHelper
import com.metacto.core.deepLink.DeepLinkManager
import com.metacto.core.deepLink.DeepLinkParser
import com.metacto.core.deepLink.IDeepLinkManager
import com.metacto.core.ui.phoneNumber.IPhoneNumberManager
import com.metacto.core.ui.phoneNumber.PhoneNumberManager
import com.metacto.core.utils.remoteConfigs.FirebaseRemoteConfigs
import com.metacto.core.utils.remoteConfigs.IRemoteConfigs
import com.metacto.core.ui.resources.IResourceProvider
import com.metacto.core.ui.resources.ResourceProvider
import com.metacto.strapikmm.auth.Authenticator
import com.metacto.strapikmm.auth.FirebaseAuthenticator
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.repos.LogoutUseCase
import com.metacto.strapikmm.util.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.auth
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import org.koin.dsl.module
import kotlin.reflect.KClass
import com.metacto.strapikmm.repos.AuthRepository as StrapiAuthRepository
import com.metacto.strapikmm.repos.UserRepository as StrapiUserRepository

fun <T : SerializableNetworkError> coreModule(
    environment: CoreEnvironment,
    actionCodeSettings: ActionCodeSettings,
    appStorageName: String,
    shouldShowActualErrorMessages: Boolean,
    errorClass: KClass<T>,
    deepLinkParsers: Map<String, DeepLinkParser> = emptyMap()
) = module {

    includes(
        corePlatformModule(
            appStorageName,
            environment,
            shouldShowActualErrorMessages,
            errorClass,
        )
    )

    includes(coreViewModelsModule)


}