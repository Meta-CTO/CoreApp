package com.metacto.core.di

import com.metacto.core.domain.repos.RepositoriesFactory
import com.metacto.core.presentation.base.CommonViewModel
import com.metacto.core.utils.DispatchersProvider
import com.metacto.core.utils.IDispatchersProvider
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.ResourceProvider
import com.metacto.core.utils.imagePreloader.IPreloader
import com.metacto.core.utils.imagePreloader.Preloader
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
}

actual inline fun <reified T : CommonViewModel> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return viewModel(qualifier, definition)
}