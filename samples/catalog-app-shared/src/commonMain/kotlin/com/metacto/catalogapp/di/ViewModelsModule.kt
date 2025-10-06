package com.metacto.catalogapp.di

// MARK: Add imports
import com.metacto.catalogapp.presentation.main.applepay.ApplePayViewModel
import com.metacto.catalogapp.presentation.navManager.NavManagerViewModel
import com.metacto.catalogapp.presentation.mediaPicker.MediaPickerViewModel
import com.metacto.catalogapp.presentation.lottie.LottieViewModel
import com.metacto.catalogapp.presentation.app.app.AppViewModel
import com.metacto.catalogapp.presentation.files.FilesSamplesViewModel
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesViewModel
import com.metacto.catalogapp.presentation.imagePreloader.ImagePreloaderViewModel
import com.metacto.catalogapp.presentation.main.MainViewModel
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerViewModel
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesViewModel
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberViewModel
import com.metacto.core.ui.di.commonViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { ApplePayViewModel() }
    commonViewModel { NavManagerViewModel() }
    commonViewModel { MediaPickerViewModel() }
    commonViewModel { LottieViewModel() }
    commonViewModel { ImagePreloaderViewModel() }
    commonViewModel { MediaManagerViewModel() }
    commonViewModel { PhoneNumberViewModel() }
    commonViewModel { NotificationsSamplesViewModel() }
    commonViewModel { FilesSamplesViewModel() }
    commonViewModel { ImagePickerSamplesViewModel() }
    commonViewModel { MainViewModel() }
    single { AppViewModel() }
}
