package com.sampleApp.app.presentation.home

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.globalState.models.MessagePopupParams
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.presentation.youtube.YoutubeScreen
import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.home.HomeContract.Companion.VIDEOS_LIST
import com.sampleApp.app.presentation.home.HomeContract.Effect
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.test.TestScreen

class HomeViewModel(
    private val deeplinkManager: IDeepLinkManager,
    private val phoneNumberManager: IPhoneNumberManager
) : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()

        Event.NavToYoutubeScreen -> {
            navManager.navigate(YoutubeScreen("Gmhk7mWG050"))
        }

        Event.NavToTestScreen -> {
            navManager.navigate(TestScreen)
        }

        is Event.ChangeCurrentVideo -> {
            setState { copy(currentVideo = VIDEOS_LIST[event.index]) }
            currentState.videoController?.play()
            Unit
        }

        is Event.VideoPlayerControllerCreated -> {
            setState { copy(videoController = event.controller) }
        }

        is Event.OpenPicker -> {
            navManager.navigateToBottomSheet(
                ItemPickerSheet(
                    selectedItem = currentState.pickedItem,
                    items = (0..20).map {
                        PickerItemUIModel(
                            key = it.toString(),
                            title = "Item $it"
                        )
                    }
                )
            )
        }

        Event.NavigateToCameraScreen -> {
            globalState.messagePopup(
                params =
                MessagePopupParams(
                    body = "These data give a picture of your physical health. Factors taken into account include how much you exercise and how much energy you burn while active.\n" +
                            "\n" +
                            "Vigorous, regular exercise is directly correlated with good physical health, and longer exercise sessions are strong indicators of endurance and overall fitness. ",
                    description = "Sources:\n" +
                            "Shaffer, F., & Ginsberg, J. P. (2017). An overview of heart rate variability metrics and norms. Frontiers in Public Health, 5, 258.\n" +
                            "Brosschot, J. F., van Dijk, E., & Thayer, J. F. (2007). Daily worry is related to low heart rate variability during waking and the subsequent nocturnal sleep period. International Journal of Psychophysiology, 63(1), 39-47."
                )
            )
        }

        Event.RequestCameraPermClicked -> {
            executeSilent({
                permissionManager.requestPermission(Permission.CAMERA)
                setState { copy(cameraPermState = PermissionState.Granted) }
            })
        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        executeSilent({
            val cameraPermState = permissionManager.getPermissionState(Permission.CAMERA)
            setState {
                copy(
                    cameraPermState = cameraPermState
                )
            }
        })

        val validPhoneNumber = phoneNumberManager.getValidPhoneNumber("201555056563", "EG")
        println("phoneNumber: $validPhoneNumber")

        observeItemPickerResults()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun observeItemPickerResults() {
        navManager.collectNavResult<ItemPickerSheet, PickerItemUIModel> { pickedItem ->
            setState { copy(pickedItem = pickedItem) }
        }
    }
}
