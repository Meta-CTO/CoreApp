package com.metacto.core.ui.imagepicker.di

import com.metacto.core.ui.di.commonViewModel
import com.metacto.core.ui.imagepicker.sheet.ImagePickerViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun imagePickerModule() = module {
    // Common dependencies can be added here

    includes(platformModule())

    commonViewModel {
        ImagePickerViewModel()
    }
}

internal expect fun platformModule(): Module