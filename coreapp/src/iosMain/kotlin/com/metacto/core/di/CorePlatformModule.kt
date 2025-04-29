package com.metacto.core.di

import coil3.PlatformContext
import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.domain.repos.forceUpdate.ForceUpdateRepository
import com.metacto.core.permissions.IPermissionManager
import com.metacto.core.permissions.PermissionManager
import com.metacto.core.presentation.base.CommonViewModel
import com.metacto.core.presentation.camera.CameraController
import com.metacto.core.presentation.camera.CameraEngine
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.itemPicker.NativeItemPicker
import com.metacto.core.utils.calendar.CalendarManager
import com.metacto.core.utils.calendar.ICalendarManager
import com.metacto.core.utils.eventBroadcaster.EventBroadcaster
import com.metacto.core.utils.imagePreloader.IPreloader
import com.metacto.core.utils.imagePreloader.Preloader
import com.metacto.core.utils.language.ILanguageManager
import com.metacto.core.utils.language.LanguageManager
import com.metacto.core.utils.launchers.IIntentLauncher
import com.metacto.core.utils.launchers.IntentLauncher
import com.metacto.core.utils.media.IMediaManager
import com.metacto.core.utils.media.MediaManager
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

    single<IPermissionManager> {
        PermissionManager(
            delegateFactory = get()
        )
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

    single<ICalendarManager> {
        CalendarManager()
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

    factory { (defaultCamera: CameraLens?) ->
        val cameraEngine = CameraEngine(
            defaultCamera = defaultCamera ?: CameraLens.BACK
        )
        CameraController(
            permissionManager = get(),
            cameraEngine = cameraEngine
        )
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