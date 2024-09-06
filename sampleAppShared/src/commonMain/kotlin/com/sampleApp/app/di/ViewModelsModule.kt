package com.sampleApp.app.di

// MARK: Add imports
import com.sampleApp.app.presentation.test.TestViewModel
import com.sampleApp.app.presentation.profile.ProfileViewModel
import com.metacto.core.di.commonViewModel
import com.sampleApp.app.presentation.app.app.AppViewModel
import com.sampleApp.app.presentation.home.HomeViewModel
import com.sampleApp.app.presentation.main.MainViewModel
import com.sampleApp.app.presentation.youtube.YoutubeViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { TestViewModel() }
    commonViewModel { ProfileViewModel() }
    commonViewModel { HomeViewModel() }
    commonViewModel { MainViewModel() }
    commonViewModel { YoutubeViewModel() }
    single { AppViewModel() }
}
