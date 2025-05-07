package com.metacto.catalogapp.presentation.phoneNumber

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberContract.Effect
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberContract.Event
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberContract.State
import com.metacto.core.phone.IPhoneNumberManager
import com.metacto.core.ui.globalState.models.SnackBarParams
import com.metacto.core.ui.globalState.models.SnackBarType
import org.koin.core.component.inject

class PhoneNumberViewModel : BaseViewModel<State, Event, Effect>() {
    private val phoneManager by inject<IPhoneNumberManager>()

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        is Event.OnPhoneNumberChanged -> handlePhoneNumberChange(event.phoneNumber)
        is Event.OnCountryCodeChanged -> handleCountryCodeChange(event.countryCode)
        Event.ValidatePhoneNumber -> handlePhoneNumberValidation()
        Event.RequestValidPhoneNumber -> handleValidPhoneNumberRequest()
        Event.RequestFormattedPhoneNumber -> handleFormattedPhoneNumberRequest()
        Event.RequestE164PhoneNumber -> handleE164PhoneNumberRequest()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handlePhoneNumberChange(phoneNumber: String) {
        setState { copy(phoneNumber = phoneNumber) }
    }

    private fun handleCountryCodeChange(countryCode: String) {
        setState { copy(countryCode = countryCode) }
    }

    private fun handlePhoneNumberValidation() {
        val isValidPhoneNumber = phoneManager.isValidPhoneNumber(
            number = currentState.phoneNumber,
            countryCode = currentState.countryCode
        )
        coreGlobalState.snackBar(
            SnackBarParams(
                message = "Phone number status is: $isValidPhoneNumber",
                type = if (isValidPhoneNumber) SnackBarType.SUCCESS else SnackBarType.ERROR,
            )
        )
    }

    private fun handleValidPhoneNumberRequest() {
        val validPhoneNumber = phoneManager.getValidPhoneNumber(
            number = currentState.phoneNumber,
            countryCode = currentState.countryCode
        )
        coreGlobalState.snackBar(
            SnackBarParams(
                message = "Valid phone number is: $validPhoneNumber",
                type = if (validPhoneNumber != null) SnackBarType.SUCCESS else SnackBarType.ERROR,
            )
        )
    }

    private fun handleFormattedPhoneNumberRequest() {
        val formattedPhoneNumber = phoneManager.getFormattedPhoneNumber(
            number = currentState.phoneNumber,
            countryCode = currentState.countryCode
        )
        coreGlobalState.snackBar(
            SnackBarParams(
                message = "Formatted phone number is: $formattedPhoneNumber",
                type = if (formattedPhoneNumber != null) SnackBarType.SUCCESS else SnackBarType.ERROR,
            )
        )
    }

    private fun handleE164PhoneNumberRequest() {
        val formattedPhoneNumber = phoneManager.getFormattedPhoneNumber(
            number = currentState.phoneNumber,
            countryCode = currentState.countryCode
        )
        coreGlobalState.snackBar(
            SnackBarParams(
                message = "E164 formatted phone number is: $formattedPhoneNumber",
                type = if (formattedPhoneNumber != null) SnackBarType.SUCCESS else SnackBarType.ERROR,
            )
        )
    }
}
