package com.metacto.core.di

import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.presentation.base.CommonViewModel
import com.metacto.core.utils.DispatchersProvider
import com.metacto.core.utils.IDispatchersProvider
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.ResourceProvider
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.utils.loadableBundle
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.Foundation.NSBundle

actual fun corePlatformModule(appStorageName: String) = module {
    single<IDispatchersProvider> {
        DispatchersProvider()
    }

    single {
        RepositoriesFactory(
            environment = get(),
            appStorageName = appStorageName
        )
    }

    single<IResourceProvider> {
        ResourceProvider(
            bundle = NSBundle.loadableBundle(MR::class.qualifiedName.orEmpty())
        )
    }
}

actual inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}