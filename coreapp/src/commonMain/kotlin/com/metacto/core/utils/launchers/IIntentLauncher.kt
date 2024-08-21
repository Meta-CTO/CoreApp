package com.metacto.core.utils.launchers

interface IIntentLauncher {
    fun launchEmail(
        email: String,
        subject: String? = null,
        body: String? = null
    )

    fun launchStore(appId: String)

    fun launchShareText(text: String)

    fun launchPhone(phone: String)

    fun launchBrowser(url: String)
}