package com.metacto.core.presentation.globalState

import androidx.compose.runtime.State
import com.metacto.core.presentation.globalState.models.ChoicesPopupParams
import com.metacto.core.presentation.globalState.models.ConfirmationPopupParams
import com.metacto.core.presentation.globalState.models.DatePickerParams
import com.metacto.core.presentation.globalState.models.ForceUpdatePopupParams
import com.metacto.core.presentation.globalState.models.ItemPickerParams
import com.metacto.core.presentation.globalState.models.LoadingType
import com.metacto.core.presentation.globalState.models.MessagePopupParams
import com.metacto.core.presentation.globalState.models.OverrideUserPopupParams
import com.metacto.core.presentation.globalState.models.SnackBarParams
import com.metacto.core.presentation.globalState.models.SuccessPopupParams
import com.metacto.core.presentation.globalState.models.TimePickerParams

interface ICoreGlobalState {
    val appLoadedState: State<Boolean>
    val loadingState: State<LoadingType>
    val messagePopupState: State<MessagePopupParams?>
    val overrideUserPopupState: State<OverrideUserPopupParams?>
    val successPopupState: State<SuccessPopupParams?>
    val confirmationPopupState: State<ConfirmationPopupParams?>
    val forceUpdatePopupState: State<ForceUpdatePopupParams?>
    val choicesPopupState: State<ChoicesPopupParams?>
    val datePickerState: State<DatePickerParams?>
    val timePickerState: State<TimePickerParams?>
    val itemPickerState: State<ItemPickerParams?>
    val snackBarState: State<SnackBarParams>
    val dismissKeyboardState: State<Boolean>
    val isStatusBarDarkState: State<Boolean>
    val isNavigationBarDarkState: State<Boolean>

    fun idle()
    fun setAppLoaded()
    fun loading(type: LoadingType)
    fun messagePopup(params: MessagePopupParams)
    fun successPopup(params: SuccessPopupParams)
    fun confirmationPopup(params: ConfirmationPopupParams)
    fun forceUpdatePopup(params: ForceUpdatePopupParams)
    fun choicesPopup(params: ChoicesPopupParams)
    fun datePicker(params: DatePickerParams)
    fun timePicker(params: TimePickerParams)
    fun itemPicker(params: ItemPickerParams)
    fun snackBar(params: SnackBarParams)
    fun overrideUserPopup(params: OverrideUserPopupParams)
    fun hideSnackBar(delay: Long = 0)
    fun dismissKeyboard()
    fun resetDismissKeyboardState()
    fun setStatusBarDark()
    fun setStatusBarLight()
    fun setNavigationBarDark()
    fun setNavigationBarLight()
}
