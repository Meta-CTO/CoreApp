package com.sampleApp.app.presentation.home

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.presentation.youtube.YoutubeScreen
import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.metacto.strapikmm.sharedpreference.KmmPreference
import com.metacto.strapikmm.util.Logger
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.camera.CameraScreen
import com.sampleApp.app.presentation.home.HomeContract.Companion.VIDEOS_LIST
import com.sampleApp.app.presentation.home.HomeContract.Effect
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.test.TestScreen

class HomeViewModel(
    private val deeplinkManager: IDeepLinkManager,
    private val kmmPreference: KmmPreference
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
            executeSilent({
                permissionManager.requestPermission(Permission.CAMERA)
                permissionManager.requestPermission(Permission.RECORD_AUDIO)
                navManager.navigate(CameraScreen)
            })
        }

        Event.RequestCameraPermClicked -> {
//            executeSilent({
//                permissionManager.requestPermission(Permission.CAMERA)
//                setState { copy(cameraPermState = PermissionState.Granted) }
//            })

            Logger("HOMEVIEWMODEL").log(kmmPreference.getSecureInt("INT_VALUE", -1).toString())
            Logger("HOMEVIEWMODEL").log(kmmPreference.getSecureBool("BOOL_VALUE", false).toString())
            Logger("HOMEVIEWMODEL").log(kmmPreference.getSecureLong("LONG_VALUE", 2000000000).toString())
            Logger("HOMEVIEWMODEL").log(kmmPreference.getSecureDouble("DOUBLE_VALUE", 100.1).toString())
            Logger("HOMEVIEWMODEL").log(kmmPreference.getSecureFloat("FLOAT_VALUE", -1f).toString())
            Logger("HOMEVIEWMODEL").log(kmmPreference.getSecureString("STRING_VALUE").toString())
        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return
        kmmPreference.putSecureInt("INT_VALUE", 1)
        kmmPreference.putSecureBool("BOOL_VALUE", true)
        kmmPreference.putSecureLong("LONG_VALUE", 12132131231)
        kmmPreference.putSecureFloat("FLOAT_VALUE", 12F)
        kmmPreference.putSecureDouble("DOUBLE_VALUE", 1212121.1)
        kmmPreference.putSecureString("STRING_VALUE","Farid")

        // Init
        executeSilent({
            val cameraPermState = permissionManager.getPermissionState(Permission.CAMERA)
            setState {
                copy(
                    cameraPermState = cameraPermState
                )
            }
        })
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
