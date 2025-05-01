package com.metacto.core.di

import coil3.PlatformContext
import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.domain.repos.forceUpdate.ForceUpdateRepository
import com.metacto.core.ui.base.CommonViewModel
import com.metacto.core.ui.components.itemPicker.NativeItemPicker
import com.metacto.core.eventBroadcaster.EventBroadcaster
import com.metacto.core.ui.imagePreloader.IPreloader
import com.metacto.core.ui.imagePreloader.Preloader
import com.metacto.core.utils.language.ILanguageManager
import com.metacto.core.utils.language.LanguageManager
import com.metacto.core.ui.launchers.IIntentLauncher
import com.metacto.core.ui.launchers.IntentLauncher
import com.metacto.core.ui.media.IMediaManager
import com.metacto.core.ui.media.MediaManager
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.repos.AppConfigurationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import io.michaelrocks.libphonenumber.kotlin.MetadataLoader
import io.michaelrocks.libphonenumber.kotlin.metadata.init.ComposeResourceMetadataLoader
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import kotlin.reflect.KClass

actual fun <T : SerializableNetworkError> corePlatformModule(
    appStorageName: String,
    coreEnvironment: CoreEnvironment,
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

    single<IIntentLauncher> {
        IntentLauncher()
    }

    single<ILanguageManager> {
        LanguageManager(get())
    }

    single<MetadataLoader> {
        ComposeResourceMetadataLoader()
    }

    single<AppConfigurationRepository> {
        AppConfigurationRepository(
            appConfigurationService = get(),
            sharedPreference = get(),
            appConfigurationExpirationInMinutes = coreEnvironment.appConfigurationExpirationInMinutes
        )
    }

    single<ForceUpdateRepository> {
        ForceUpdateRepository(
            appEnvironment = get(),
            appConfigurationRepository = get(),
            remoteConfigs = get(),
        )
    }

    single<IMediaManager> {
        MediaManager()
    }

    single {
        NativeItemPicker(get())
    }
}

actual inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}