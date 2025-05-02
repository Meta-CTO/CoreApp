package com.metacto.sampleapp.di

// MARK: Add imports
import com.metacto.sampleapp.presentation.notifications.NotificationsSamplesViewModel
import com.metacto.core.ui.di.commonViewModel
import com.metacto.sampleapp.presentation.app.app.AppViewModel
import com.metacto.sampleapp.presentation.main.MainViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    commonViewModel { NotificationsSamplesViewModel() }
    commonViewModel { MainViewModel() }
    single { AppViewModel() }
}
