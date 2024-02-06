package com.metacto.core.utils.phoneNumber

import com.metacto.core.domain.CoreConstants.US_COUNTRY_CODE

val Class = IPhoneNumberManager::class

interface IPhoneNumberManager {
    fun getValidPhoneNumber(
        number: String,
        countryCode: String = US_COUNTRY_CODE
    ): String?

    fun getFormattedPhoneNumber(
        number: String,
        countryCode: String = US_COUNTRY_CODE
    ): String?

    fun getE164FormattedPhoneNumber(
        number: String,
        countryCode: String = US_COUNTRY_CODE
    ): String?

    fun isValidPhoneNumber(
        number: String,
        countryCode: String = US_COUNTRY_CODE
    ): Boolean
}