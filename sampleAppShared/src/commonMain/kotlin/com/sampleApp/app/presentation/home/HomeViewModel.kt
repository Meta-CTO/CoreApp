package com.sampleApp.app.presentation.home

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.globalState.models.DatePickerParams
import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.imagePicker.ImagePickerSheet
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.presentation.youtube.YoutubeScreen
import com.sampleApp.app.resources.file
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.camera.CameraScreen
import com.sampleApp.app.presentation.home.HomeContract.Companion.VIDEOS_LIST
import com.sampleApp.app.presentation.home.HomeContract.Effect
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.test.TestScreen
import com.sampleApp.app.resources.Res
import com.sampleApp.app.resources.d_languages
import com.sampleApp.app.resources.hello_s
import com.sampleApp.app.resources.hello_s1_from_s2_to_s3
import com.sampleApp.app.resources.hello_world
import com.sampleApp.app.resources.my_languages

class HomeViewModel : BaseViewModel<State, Event, Effect>() {

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

        Event.AddToCalendar -> {
            globalState.datePicker(
                DatePickerParams(onDatePicked = {

                })
            )
        }

        Event.OpenBrowser -> {
            intentLauncher.launchBrowser("https://www.facebook.com/")
        }

        Event.OpenPhone -> {
            intentLauncher.launchPhone("+201555056563")
        }

        Event.OpenStore -> {
            intentLauncher.launchStore("")
        }

        Event.ShareEmail -> {
            intentLauncher.launchEmail("uni.fareed@gmail.com", "Test subject", "test body")
        }

        Event.ShareImage -> {
            shareImage()
        }

        Event.ShareText -> {
            intentLauncher.shareText("Hello share text")
        }

        Event.OpenImagePicker -> {
            navManager.navigateToBottomSheet(ImagePickerSheet(showDeleteAction = true))
        }

        Event.NavigateToCameraScreen -> {
            navManager.navigate(CameraScreen)
        }

        Event.HideLoading -> hideLoading()
        Event.ShowAppLottieLoading -> showLoading(LoadingType.Lottie(Res.file.app_loading))
        Event.TestFormattedPluralStringResource -> {
            val quantity = listOf(1, 50).random()
            val value =
                resourceProvider.getPluralString(Res.plurals.d_languages, quantity, quantity)
            showError(value)
        }

        Event.TestFormattedStringResource -> {
            val value = resourceProvider.getString(
                Res.string.hello_s1_from_s2_to_s3,
                "Shamy",
                "Damietta",
                "Zeiny"
            )
            showError(value)
        }

        Event.TestPluralStringResource -> {
            val quantity = listOf(1, 50).random()
            val value = resourceProvider.getPluralString(Res.plurals.my_languages, quantity)
            showError(value)
        }

        Event.TestStringResource -> {
            val value = resourceProvider.getString(Res.string.hello_world)
            showError(value)
        }

        Event.NativeItemPicker -> {
            nativeItemPicker(
                items = (0..20).map {
                    PickerItemUIModel(
                        key = it.toString(),
                        title = "Item $it"
                    )
                },
                selectedItem = currentState.selectedNativePickerItem,
                onItemSelected = { item ->
                    setState { copy(selectedNativePickerItem = item) }
                }
            )
        }

        Event.RequestCameraPermClicked ->{
            executeSilent({
                permissionManager.requestPermission(Permission.CAMERA)
                setState { copy(cameraPermState = PermissionState.Granted) }
            })
        }
    }

    private fun shareImage() = executeCatching(
        loadingType = super.defaultLoadingType,
        block = {
            intentLauncher.shareImage("https://upload.wikimedia.org/wikipedia/commons/4/40/Image_test.png?20141030190340")
        }
    )

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
