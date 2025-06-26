package com.sampleApp.app.presentation.home

import coil3.network.httpHeaders
import coil3.request.ErrorResult
import com.metacto.core.domain.repos.UploadRepository
import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.globalState.models.MessagePopupParams
import com.metacto.core.presentation.itemPicker.ItemPickerSheet
import com.metacto.core.presentation.itemPicker.models.PickerItemUIModel
import com.metacto.core.presentation.models.ImageUIModel
import com.metacto.core.presentation.models.copyOrCreate
import com.metacto.core.presentation.youtube.YoutubeScreen
import com.metacto.core.utils.date.formatToRelativeDate
import com.metacto.core.utils.deepLink.IDeepLinkManager
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.home.HomeContract.Companion.VIDEOS_LIST
import com.sampleApp.app.presentation.home.HomeContract.Effect
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.test.TestScreen
import com.sampleApp.app.presentation.permissions.PermissionsTestScreen
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class HomeViewModel(
    private val deeplinkManager: IDeepLinkManager,
    private val phoneNumberManager: IPhoneNumberManager,
    private val uploadRepository: UploadRepository
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

        Event.NavToPermissionsTest -> {
            navManager.navigate(PermissionsTestScreen)
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
                                "Shaffer, F., & Ginsberg, J. P. (2017). An overview of heart rate variability metrics and norms. Frontiers in Public Health, 5, 258.\n" +
                                "Brosschot, J. F., van Dijk, E., & Thayer, J. F. (2007). Daily worry is related to low heart rate variability during waking and the subsequent nocturnal sleep period. International Journal of Psychophysiology, 63(1), 39-47."
                    )
            )
        }

        is Event.ImageFailedLoading -> onImageFailedLoading(event.error)
    }

    private fun init() {
        if (currentState.isInitialized) return

        setState { copy(image = ImageUIModel(url = "https://scstage103-cd.joycemeyer.org/-/media/JoyceMeyer/Grow-Your-Faith/Daily-Devo/DevoImage17.jpg")) }

        val date1 = LocalDate(2021, 1, 1)
        val date2 = LocalDate(2025, 2, 25)
        val date3 = LocalDateTime(
            date = LocalDate(2025, 2, 25),
            time = LocalTime(14, 15)
        )
        val date4 = LocalDateTime(
            date = LocalDate(2025, 2, 25),
            time = LocalTime(14, 10)
        )
        val date5 = LocalDateTime(
            date = LocalDate(2025, 2, 25),
            time = LocalTime(13, 0)
        )

        println("Formatted date ==== date1: ${date1.formatToRelativeDate()}")
        println("Formatted date ==== date2: ${date2.formatToRelativeDate()}")
        println("Formatted date ==== date3: ${date3.formatToRelativeDate()}")
        println("Formatted date ==== date4: ${date4.formatToRelativeDate()}")
        println("Formatted date ==== date5: ${date5.formatToRelativeDate()}")

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

        setState { copy(isInitialized = true) }
    }

    private fun observeItemPickerResults() {
        navManager.collectNavResult<ItemPickerSheet, PickerItemUIModel> { pickedItem ->
            setState { copy(pickedItem = pickedItem) }
        }
    }

    private fun onImageFailedLoading(errorResult: ErrorResult) = executeSilent({
        val error = errorResult.throwable.message.orEmpty()
        val url = currentState.image?.url ?: return@executeSilent
        errorResult.request.data
        if(error.contains("makeFromEncoded", true)) {
            val bytes =  uploadRepository.downloadBytes(url, errorResult.request.httpHeaders.asMap())
            setState { copy(image = image.copyOrCreate(bytes = bytes)) }
        }
    })
}