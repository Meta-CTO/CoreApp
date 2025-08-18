package com.metacto.core.phone

val Class = IPhoneNumberManager::class

interface IPhoneNumberManager {
    fun getValidPhoneNumber(
        number: String,
        countryCode: String?
    ): String?

    fun getFormattedPhoneNumber(
        number: String,
        countryCode: String?
    ): String?

    fun getE164FormattedPhoneNumber(
        number: String,
        countryCode: String?
    ): String?

    fun isValidPhoneNumber(
        number: String,
        countryCode: String?
    ): Boolean
}