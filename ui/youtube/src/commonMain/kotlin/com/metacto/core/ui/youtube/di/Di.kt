package com.metacto.core.ui.youtube.di

import com.metacto.core.ui.di.commonViewModel
import com.metacto.core.ui.youtube.screen.YoutubeViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun youtubeModule() = module {
    // Common dependencies can be added here

    includes(platformModule())

    commonViewModel {
        YoutubeViewModel()
    }
}

internal expect fun platformModule(): Module