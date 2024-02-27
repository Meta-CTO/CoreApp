package com.metacto.core.di

import com.google.firebase.messaging.FirebaseMessaging
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.permissions.PermissionManager
import com.metacto.core.presentation.base.CommonViewModel
import com.metacto.core.utils.DispatchersProvider
import com.metacto.core.utils.IDispatchersProvider
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.ResourceProvider
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.metacto.core.utils.imagePreloader.IPreloader
import com.metacto.core.utils.imagePreloader.Preloader
import com.metacto.core.utils.launchers.IIntentLauncher
import com.metacto.core.utils.launchers.IntentLauncher
import com.metacto.core.utils.notificationManager.INotificationManager
import com.metacto.core.utils.notificationManager.NotificationManager
import com.metacto.core.utils.pushNotifications.FirebasePushNotificationsManager
import com.metacto.core.utils.pushNotifications.IPushNotificationsManager
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

actual fun corePlatformModule(appStorageName: String) = module {
    single<IDispatchersProvider> {
        DispatchersProvider()
    }

    single {
        RepositoriesFactory(
            context = androidContext(),
            environment = get(),
            appStorageName = appStorageName
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
            applicationContext = androidContext()
        )
    }
}

actual inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return viewModel(qualifier, definition)
}