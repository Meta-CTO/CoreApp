package com.sampleApp.app.di

import com.metacto.core.CoreEnvironment
import com.metacto.core.di.coreModule
import com.metacto.strapikmm.errorhandling.NetworkError
import com.sampleApp.app.constants.AppEnvironment
import com.sampleApp.app.deepLink.DEEP_LINK_PARSERS
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.AndroidPackageName
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    environment: CoreEnvironment
) = initKoin(
    environment = environment,
    appDeclaration = {}
)

fun initKoin(
    environment: CoreEnvironment,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        createCoreModule(environment),
        appModule,
        platformModule,
        viewModelsModule,
        repositoriesModule
    )
}

private fun createCoreModule(environment: CoreEnvironment) = coreModule(
    environment = environment,
    appStorageName = "SampleApp",
    shouldShowActualErrorMessages = environment != AppEnvironment.prod(),
    errorClass = NetworkError::class,
    actionCodeSettings = ActionCodeSettings(
        iOSBundleId = "com.sampleApp.app",
        androidPackageName = AndroidPackageName(
            packageName = "com.sampleApp.app",
            installIfNotAvailable = true,
            minimumVersion = "1"
        ),
        url = "",
        canHandleCodeInApp = true,
        dynamicLinkDomain = "links.sampleApp.com",
    ),
    deepLinkParsers = DEEP_LINK_PARSERS
)