package com.metacto.core.di

import com.metacto.core.presentation.imagePicker.ImagePickerViewModel
import com.metacto.core.presentation.itemPicker.ItemPickerViewModel
import com.metacto.core.presentation.options.OptionsViewModel
import com.metacto.core.presentation.youtube.YoutubeViewModel
import org.koin.dsl.module

internal val coreViewModelsModule = module {
    commonViewModel { ImagePickerViewModel() }
    commonViewModel { OptionsViewModel() }
    commonViewModel { ItemPickerViewModel() }
    commonViewModel { YoutubeViewModel() }
}