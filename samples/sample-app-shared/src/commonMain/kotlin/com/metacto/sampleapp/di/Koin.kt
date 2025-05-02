package com.metacto.sampleapp.di

import com.metacto.core.di.coreModule
import com.metacto.core.files.di.filesModule
import com.metacto.core.notifications.NotificationsConfigs
import com.metacto.core.notifications.di.notificationsModule
import com.metacto.core.phone.di.phoneCoreModule
import com.metacto.core.ui.camera.di.cameraModule
import com.metacto.core.ui.di.coreUIModule
import com.metacto.core.ui.imagepicker.di.imagePickerModule
import com.metacto.core.ui.mediaplayers.di.mediaPlayersModule
import com.metacto.core.ui.phone.di.phoneUIModule
import com.metacto.core.ui.youtube.di.youtubeModule
import com.metacto.sampleapp.constants.AppEnvironment
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    environment: AppEnvironment
) = initKoin(
    environment = environment,
    appDeclaration = {}
)

fun initKoin(
    environment: AppEnvironment,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        appModule,
        platformModule,
        viewModelsModule,
        domainModule,
        coreModule(environment.coreConfigs),
        coreUIModule(environment.coreUIConfigs),
        filesModule(),
        notificationsModule(
            NotificationsConfigs(
                askNotificationPermissionOnStart = true
            )
        ),
        phoneCoreModule(),
        mediaPlayersModule(),
        cameraModule(),
        youtubeModule(),
        imagePickerModule(),
        phoneUIModule()
    )
}