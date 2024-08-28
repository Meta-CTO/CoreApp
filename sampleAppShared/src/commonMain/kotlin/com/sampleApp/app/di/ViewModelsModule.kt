package com.sampleApp.app.di

// MARK: Add imports
import com.metacto.core.di.commonViewModel
import com.sampleApp.app.presentation.app.app.AppViewModel
import com.sampleApp.app.presentation.main.MainViewModel
import com.sampleApp.app.presentation.youtube.YoutubeViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { MainViewModel() }
    commonViewModel { YoutubeViewModel() }
    single { AppViewModel() }
}