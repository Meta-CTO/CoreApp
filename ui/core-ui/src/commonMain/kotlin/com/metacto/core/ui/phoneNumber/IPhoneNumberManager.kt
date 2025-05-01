package com.metacto.core.ui.phoneNumber

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