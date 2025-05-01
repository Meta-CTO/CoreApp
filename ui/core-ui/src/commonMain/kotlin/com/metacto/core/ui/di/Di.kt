package com.metacto.core.ui.di

import com.metacto.core.ui.CoreUIConfigs
import com.metacto.core.ui.components.imagePicker.ImagePickerViewModel
import com.metacto.core.ui.components.itemPicker.ItemPickerViewModel
import com.metacto.core.ui.components.options.OptionsViewModel
import com.metacto.core.ui.components.youtube.screen.YoutubeViewModel
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.ui.phoneNumber.IPhoneNumberManager
import com.metacto.core.ui.phoneNumber.PhoneNumberManager
import com.metacto.core.ui.resources.IResourceProvider
import com.metacto.core.ui.resources.ResourceProvider
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
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

    commonViewModel {
        YoutubeViewModel()
    }

    single {
        NavManager()
    }

    single<IPhoneNumberManager> {
        PhoneNumberManager(get())
    }

    single {
        PhoneNumberUtil.createInstance(get())
    }

    single<IResourceProvider> {
        ResourceProvider
    }
}

internal expect fun platformModule(): Module