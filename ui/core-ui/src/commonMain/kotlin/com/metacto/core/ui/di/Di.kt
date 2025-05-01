package com.metacto.core.ui.di

import com.metacto.core.ui.CoreUIConfigs
import com.metacto.core.ui.components.imagePicker.ImagePickerViewModel
import com.metacto.core.ui.components.itemPicker.ItemPickerViewModel
import com.metacto.core.ui.components.options.OptionsViewModel
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.ui.resources.IResourceProvider
import com.metacto.core.ui.resources.ResourceProvider
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreUIModule(configs: CoreUIConfigs) = module {
    // Common dependencies can be added here

    includes(platformModule())

    single {
        configs
    }

    commonViewModel {
        ImagePickerViewModel()
    }

    commonViewModel {
        OptionsViewModel()
    }

    commonViewModel {
        ItemPickerViewModel()
    }

    single {
        NavManager()
    }

    single<IResourceProvider> {
        ResourceProvider
    }
}

internal expect fun platformModule(): Module