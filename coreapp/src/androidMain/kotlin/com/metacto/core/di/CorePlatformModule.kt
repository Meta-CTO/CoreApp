package com.metacto.core.di

import com.google.firebase.messaging.FirebaseMessaging
import com.metacto.core.domain.repos.ForceUpdateRepository
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.permissions.PermissionManager
import com.metacto.core.presentation.base.CommonViewModel
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.ResourceProvider
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.metacto.core.utils.imagePreloader.IPreloader
import com.metacto.core.utils.imagePreloader.Preloader
import com.metacto.core.utils.language.ILanguageManager
import com.metacto.core.utils.language.LanguageManager
import com.metacto.core.utils.launchers.IIntentLauncher
import com.metacto.core.utils.launchers.IntentLauncher
import com.metacto.core.utils.notificationManager.INotificationManager
import com.metacto.core.utils.notificationManager.NotificationManager
import com.metacto.core.utils.pushNotifications.FirebasePushNotificationsManager
import com.metacto.core.utils.pushNotifications.IPushNotificationsManager
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.repos.AppConfigurationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.metadata.source.AssetsMetadataLoader
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import kotlin.reflect.KClass

actual fun <T : SerializableNetworkError> corePlatformModule(
    appStorageName: String, shouldShowActualErrorMessages: Boolean, errorClass: KClass<T>
) = module {
    single {
        RepositoriesFactory<T>(
            context = androidContext(),
            environment = get(),
            appStorageName = appStorageName,
            shouldShowActualErrorMessages = shouldShowActualErrorMessages,
            errorClass = errorClass
        )
    }

    single<IResourceProvider> {
        ResourceProvider(androidContext())
    }

    single<IPreloader> {
        Preloader(
            context = androidContext().applicationContext
        )
    }

    single {
        EventBroadcaster
    }

    single {
        Firebase.remoteConfig
    }

    single<INotificationManager> {
        NotificationManager(
            context = androidContext()
        )
    }

    single<IPushNotificationsManager> {
        FirebasePushNotificationsManager(
            firebaseMessaging = FirebaseMessaging.getInstance()
        )
    }

    single<IPermissionManager> {
        PermissionManager(
            applicationContext = androidContext()
        )
    }

    single<IIntentLauncher> {
        IntentLauncher(
            context = androidContext()
        )
    }

    single<ILanguageManager> {
        LanguageManager()
    }

    single<MetadataLoader> {
        AssetsMetadataLoader(androidApplication().assets)
    }

    single<AppConfigurationRepository> {
        AppConfigurationRepository(
            applicationContext = androidContext(),
            appConfigurationService = get(),
            sharedPreference = get(),
            appConfigurationExpirationInMinutes = 1
        )
    }

    single<ForceUpdateRepository> {
        ForceUpdateRepository(
            appEnvironment = get(),
            applicationContext = androidContext(),
            appConfigurationRepository = get(),
            remoteConfigs = get(),
        )
    }
}

actual inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier?, noinline definition: Definition<T>
): KoinDefinition<T> {
    return viewModel(qualifier, definition)
}