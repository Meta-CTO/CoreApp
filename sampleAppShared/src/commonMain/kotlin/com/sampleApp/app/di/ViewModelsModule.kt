package com.sampleApp.app.di

// MARK: Add imports
import com.sampleApp.app.presentation.test2.test2.Test2ViewModel
import com.metacto.core.di.commonViewModel
import com.sampleApp.app.presentation.app.app.AppViewModel
import com.sampleApp.app.presentation.camera.CameraViewModel
import com.sampleApp.app.presentation.home.HomeViewModel
import com.sampleApp.app.presentation.main.MainViewModel
import com.sampleApp.app.presentation.profile.ProfileViewModel
import com.sampleApp.app.presentation.test.TestViewModel
import com.sampleApp.app.presentation.testsheet1.TestSheet1ViewModel
import com.sampleApp.app.presentation.testsheet2.TestSheet2ViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { Test2ViewModel() }
    commonViewModel { TestSheet2ViewModel() }
    commonViewModel { TestSheet1ViewModel() }
    commonViewModel { CameraViewModel() }
    commonViewModel { TestViewModel() }
    commonViewModel { ProfileViewModel() }
    commonViewModel { HomeViewModel(get(),get()) }
    commonViewModel { MainViewModel() }
    single { AppViewModel() }
}
