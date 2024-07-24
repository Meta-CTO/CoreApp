package com.sampleApp.app.di

import com.metacto.core.di.commonViewModel
import com.sampleApp.app.presentation.app.app.AppViewModel
import com.sampleApp.app.presentation.landing.splash.SplashViewModel
import com.sampleApp.app.presentation.landing.youtube.YoutubeViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    commonViewModel { AppViewModel() }
    commonViewModel { SplashViewModel(get(), get(), get(), get()) }
    commonViewModel { YoutubeViewModel() }
}