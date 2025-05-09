package com.metacto.catalogapp.di

// MARK: Add imports
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesViewModel
import com.metacto.core.ui.di.commonViewModel
import com.metacto.catalogapp.presentation.app.app.AppViewModel
import com.metacto.catalogapp.presentation.imagePicker.imagepicker.ImagePickerViewModel
import com.metacto.catalogapp.presentation.main.MainViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { NotificationsSamplesViewModel() }
    commonViewModel { ImagePickerViewModel() }
    commonViewModel { MainViewModel() }
    single { AppViewModel() }
}
