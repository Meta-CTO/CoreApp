package com.metacto.core.utils.launchers

expect class IntentLauncher: IIntentLauncher {
    override fun launchEmail(email: String, subject: String?, body: String?)

    override fun launchShareText(text: String)
}