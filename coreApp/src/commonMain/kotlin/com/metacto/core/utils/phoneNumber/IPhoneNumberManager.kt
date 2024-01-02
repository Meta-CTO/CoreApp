package com.metacto.core.utils.phoneNumber

interface IPhoneNumberManager {
    fun getValidPhoneNumber(number: String): String?

    fun getFormattedPhoneNumber(
        number: String,
        countryCode: String? = null
    ): String?

    fun getE164FormattedPhoneNumber(
        number: String,
        countryCode: String? = null
    ): String?

    fun isValidPhoneNumber(
        number: String,
        countryCode: String? = null
    ): Boolean
}