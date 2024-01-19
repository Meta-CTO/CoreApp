package com.metacto.core.utils.phoneNumber

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

    companion object {
        private const val US_COUNTRY_CODE = "US"
    }
}