package com.metacto.core.utils.launchers

interface IIntentLauncher {
    fun launchEmail(
        email: String,
        subject: String? = null,
        body: String? = null
    )

    fun launchShareText(text: String)

    fun launchPhone(phone: String)
}