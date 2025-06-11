package com.metacto.catalogapp.di

// MARK: Add imports
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleViewModel
import com.metacto.catalogapp.presentation.app.app.AppViewModel
import com.metacto.catalogapp.presentation.files.FilesSamplesViewModel
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesViewModel
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderViewModel
import com.metacto.catalogapp.presentation.main.MainViewModel
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerViewModel
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesViewModel
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberViewModel
import com.metacto.core.ui.di.commonViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { YoutubeSampleViewModel() }
    commonViewModel { ImagePreloaderViewModel() }
    commonViewModel { MediaManagerViewModel() }
    commonViewModel { PhoneNumberViewModel() }
    commonViewModel { NotificationsSamplesViewModel() }
    commonViewModel { FilesSamplesViewModel() }
    commonViewModel { ImagePickerSamplesViewModel() }
    commonViewModel { MainViewModel() }
    single { AppViewModel() }
}
