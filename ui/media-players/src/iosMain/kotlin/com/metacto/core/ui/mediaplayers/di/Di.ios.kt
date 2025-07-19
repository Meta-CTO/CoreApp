package com.metacto.core.ui.mediaplayers.di

import com.metacto.core.ui.mediaplayers.videoPlayer.VideoPlayerManager
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // iOS specific dependencies can be added here

    single<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers) {
        mutableMapOf()
    }
}