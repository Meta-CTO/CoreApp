package com.metacto.core.utils.launchers

interface IIntentLauncher {
    fun launchEmail(email: String, subject: String?, body: String?)

    fun launchShareText(text: String)
}