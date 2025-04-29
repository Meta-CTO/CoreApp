package com.metacto.core.mediaplayers.di

import com.metacto.core.mediaplayers.audioPlayer.AudioPlayerManager
import com.metacto.core.mediaplayers.videoPlayer.VideoPlayerManager
import org.koin.dsl.module

internal actual val platformModule = module {
    single<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers) {
        mutableMapOf()
    }

    single<MutableMap<String, AudioPlayerManager>>(DiQualifiers.audioPlayerManagers) {
        mutableMapOf()
    }
}