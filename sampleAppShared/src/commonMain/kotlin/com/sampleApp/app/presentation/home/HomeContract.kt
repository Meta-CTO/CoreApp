package com.sampleApp.app.presentation.home

import coil3.request.ErrorResult
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.components.videoPlayer.VideoPlayerController
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.presentation.models.ImageUIModel
import com.sampleApp.app.presentation.models.VideoItemInfo

class HomeContract {

    data class State(
        val isInitialized: Boolean = false,
        val isVideoPlaying: Boolean = true,
        val currentVideo: VideoItemInfo = VIDEOS_LIST[0],
        val videoController: VideoPlayerController? = null,
        val pickedItem: PickerItemUIModel? = null,
        val selectedNativePickerItem: PickerItem? = null,
        val cameraPermState: PermissionState? = null,
        val image: ImageUIModel?= null
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object NavToYoutubeScreen : Event()
        data object NavToTestScreen : Event()
        data object NavToPermissionsTest : Event()
        data class ChangeCurrentVideo(val index: Int) : Event()
        data class VideoPlayerControllerCreated(val controller: VideoPlayerController) : Event()
        data object OpenPicker : Event()
        data class ImageFailedLoading(val error: ErrorResult) : Event()
        data object NavigateToCameraScreen : Event()
    }

    sealed class Effect : ViewSideEffect

    companion object {
        val VIDEOS_LIST = listOf(
            VideoItemInfo(
                url = "https://www.w3schools.com/tags/mov_bbb.mp4",
                title = "Mahmoud Elshamy - Talking about the future of the world",
                artist = "Mahmoud Elshamy",
                artworkUrl = "https://mahmoudelshamy.com/index-assets/images/profile-2-250x250.png"
            ),
            VideoItemInfo(
                url = "/data/user/0/com.sampleApp.app.prod/cache/videos/recorded_video.mp4",
                title = "Talking about the future of the Compose multiplatform",
                artist = "Ahmed Elzeiny",
                artworkUrl = "https://www.hdwallpapers.in/download/car_in_fire_city_hq-normal.jpg"
            ),
            VideoItemInfo(
                url = "/data/user/0/com.sampleApp.app.prod/cache/videos/recorded_video.mp4"
            )
        )
    }
}
