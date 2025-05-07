package com.metacto.catalogapp.presentation.phoneNumber

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class PhoneNumberContract {

    data class State(
        val isInitialized: Boolean = false,
        val phoneNumber: String = "",
        val countryCode: String = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data class OnPhoneNumberChanged(val phoneNumber: String) : Event()
        data class OnCountryCodeChanged(val countryCode: String) : Event()
        data object ValidatePhoneNumber : Event()
        data object RequestValidPhoneNumber : Event()
        data object RequestFormattedPhoneNumber : Event()
        data object RequestE164PhoneNumber : Event()
    }

    sealed class Effect : ViewSideEffect
}
