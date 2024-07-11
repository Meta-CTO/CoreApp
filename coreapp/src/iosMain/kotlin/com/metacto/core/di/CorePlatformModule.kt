package com.metacto.core.di

import coil3.PlatformContext
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
import com.metacto.coreApp.MR
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import dev.icerock.moko.resources.utils.loadableBundle
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.reflect.KClass

actual fun<T : SerializableNetworkError> corePlatformModule(
    appStorageName: String,
    shouldShowActualErrorMessages: Boolean,
    errorClass: KClass<T>
) = module {
    single {
        RepositoriesFactory<T>(
            environment = get(),
            appStorageName = appStorageName,
            shouldShowActualErrorMessages = shouldShowActualErrorMessages,
            errorClass = errorClass
        )
    }

    single<IResourceProvider> {
        ResourceProvider(
            bundle = NSBundle.loadableBundle(MR::class.qualifiedName.orEmpty()),
            fileManager = NSFileManager.defaultManager()
        )
    }

    single<IPreloader> {
        Preloader(
            context = PlatformContext.INSTANCE
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
            notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        )
    }

    single<IPermissionManager> {
        PermissionManager(
            delegateFactory = get()
        )
    }

    single<IIntentLauncher> {
        IntentLauncher()
    }

    single<ILanguageManager> {
        LanguageManager()
    }
}

actual inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}