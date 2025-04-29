package com.metacto.core.ui.mediaplayers.di

import com.metacto.core.ui.mediaplayers.audioPlayer.AudioPlayerManager
import com.metacto.core.ui.mediaplayers.videoPlayer.VideoPlayerManager
import org.koin.dsl.module

internal actual val platformModule = module {
    single<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers) {
        mutableMapOf()
    }

    single<MutableMap<String, AudioPlayerManager>>(DiQualifiers.audioPlayerManagers) {
        mutableMapOf()
    }
}