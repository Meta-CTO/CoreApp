@file:OptIn(DelicateCoroutinesApi::class)

package com.metacto.core.presentation.globalState

import androidx.compose.runtime.mutableStateOf
import com.metacto.core.presentation.globalState.models.ChoicesPopupParams
import com.metacto.core.presentation.globalState.models.ConfirmationPopupParams
import com.metacto.core.presentation.globalState.models.DatePickerParams
import com.metacto.core.presentation.globalState.models.ForceUpdatePopupParams
import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.globalState.models.MessagePopupParams
import com.metacto.core.presentation.globalState.models.OverrideUserPopupParams
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SuccessPopupParams
import com.metacto.core.presentation.globalState.models.TimePickerParams
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class CoreGlobalState : ICoreGlobalState {

    override val appLoadedState = mutableStateOf(false)
    override val navigateToLogin = mutableStateOf(false)
    override val loadingState = mutableStateOf<LoadingType>(LoadingType.NoLoading)
    override val messagePopupState = mutableStateOf<MessagePopupParams?>(null)
    override val overrideUserPopupState = mutableStateOf<OverrideUserPopupParams?>(null)
    override val successPopupState = mutableStateOf<SuccessPopupParams?>(null)
    override val confirmationPopupState = mutableStateOf<ConfirmationPopupParams?>(null)
    override val forceUpdatePopupState = mutableStateOf<ForceUpdatePopupParams?>(null)
    override val choicesPopupState = mutableStateOf<ChoicesPopupParams?>(null)
    override val datePickerState = mutableStateOf<DatePickerParams?>(null)
    override val timePickerState = mutableStateOf<TimePickerParams?>(null)
    override val snackBarState = mutableStateOf(SnackBarParams.hidden())
    override val dismissKeyboardState = mutableStateOf(false)
    override val isStatusBarDarkState = mutableStateOf(false)
    override val isNavigationBarDarkState = mutableStateOf(false)
    private var hideSnackBarJob: Job? = null

    override fun idle() {
        navigateToLogin.value = false
        loadingState.value = LoadingType.NoLoading
        messagePopupState.value = null
        successPopupState.value = null
        confirmationPopupState.value = null
        forceUpdatePopupState.value = null
        choicesPopupState.value = null
        datePickerState.value = null
        timePickerState.value = null
        overrideUserPopupState.value = null
    }

    override fun navigateToLogin() {
        navigateToLogin.value = true
    }

    override fun resetNavigateToLogin() {
        navigateToLogin.value = false
    }

    override fun setAppLoaded() {
        appLoadedState.value = true
    }

    override fun loading(type: LoadingType) {
        loadingState.value = type
    }

    override fun messagePopup(params: MessagePopupParams) {
        messagePopupState.value = params
    }

    override fun successPopup(params: SuccessPopupParams) {
        successPopupState.value = params
    }

    override fun confirmationPopup(params: ConfirmationPopupParams) {
        confirmationPopupState.value = params
    }

    override fun forceUpdatePopup(params: ForceUpdatePopupParams) {
        forceUpdatePopupState.value = params
    }

    override fun choicesPopup(params: ChoicesPopupParams) {
        choicesPopupState.value = params
    }

    override fun datePicker(params: DatePickerParams) {
        datePickerState.value = params
    }

    override fun timePicker(params: TimePickerParams) {
        timePickerState.value = params
    }

    override fun snackBar(params: SnackBarParams) {
        // Show the snack bar
        snackBarState.value = params

        // Hide snack bar after duration
        hideSnackBar(SNACK_BAR_DURATION)
    }

    override fun overrideUserPopup(params: OverrideUserPopupParams) {
        overrideUserPopupState.value = params
    }

    override fun hideSnackBar(delay: Long) {
        // Schedule a job to dismiss it after delay time
        hideSnackBarJob?.cancel()
        hideSnackBarJob = GlobalScope.launch {
            delay(delay)
            snackBarState.value = snackBarState.value.copy(isVisible = false)
        }
    }

    override fun dismissKeyboard() {
        dismissKeyboardState.value = true
    }

    override fun resetDismissKeyboardState() {
        dismissKeyboardState.value = false
    }

    override fun setStatusBarDark() {
        isStatusBarDarkState.value = true
    }

    override fun setStatusBarLight() {
        isStatusBarDarkState.value = false
    }

    override fun setNavigationBarDark() {
        isNavigationBarDarkState.value = true
    }

    override fun setNavigationBarLight() {
        isNavigationBarDarkState.value = false
    }

    companion object {
        private const val SNACK_BAR_DURATION = 5000L
    }
}