package com.sampleApp.app.presentation.home

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.components.videoPlayer.VideoPlayerController
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.sampleApp.app.presentation.models.VideoItemInfo

class HomeContract {

    data class State(
        val isInitialized: Boolean = false,
        val isVideoPlaying: Boolean = true,
        val currentVideo: VideoItemInfo = VIDEOS_LIST[0],
        val videoController: VideoPlayerController? = null,
        val pickedItem: PickerItemUIModel? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object NavToYoutubeScreen : Event()
        data class ChangeCurrentVideo(val index: Int) : Event()
        data class VideoPlayerControllerCreated(val controller: VideoPlayerController) : Event()
        data object OpenPicker : Event()
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
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
            )
        )
    }
}
