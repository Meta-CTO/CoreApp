package com.sampleApp.app.presentation.main

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

data class VideoItemInfo(
    val url: String,
    val artist: String? = null,
    val title: String? = null,
    val artworkUrl: String? = null
)

class MainContract {

    data class State(
        val isInitialized: Boolean = false,
        val isVideoPlaying: Boolean = true,
        val currentVideo: VideoItemInfo = VIDEOS_LIST[0]
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object ClickMeClicked : Event()
        data class ChangeCurrentVideo(val index: Int) : Event()
    }

    sealed class Effect : ViewSideEffect

    companion object {
        val VIDEOS_LIST = listOf(
            VideoItemInfo(
                url = "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                title = "Mahmoud Elshamy - Talking about the future of the world",
                artist = "Mahmoud Elshamy",
                artworkUrl = "https://mahmoudelshamy.com/index-assets/images/profile-2-250x250.png"
            ),
            VideoItemInfo(
                url = "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8",
                title = "Talking about the future of the Compose multiplatform",
                artist = "Ahmed Elzeiny",
                artworkUrl = "https://www.hdwallpapers.in/download/car_in_fire_city_hq-normal.jpg"
            ),
            VideoItemInfo(
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
            )
        )
    }
}