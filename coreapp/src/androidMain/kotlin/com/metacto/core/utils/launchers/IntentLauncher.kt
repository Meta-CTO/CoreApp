package com.metacto.core.utils.launchers

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class IntentLauncher(
    private val applicationContext: Context
): IIntentLauncher {
    actual override fun launchEmail(
        email: String,
        subject: String?,
        body: String?
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            if (subject != null) putExtra(Intent.EXTRA_SUBJECT, subject)
            if (body != null) putExtra(Intent.EXTRA_TEXT, body)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (intent.resolveActivity(applicationContext.packageManager) != null) {
            applicationContext.startActivity(intent)
        }
    }

    actual override fun launchShareText(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(shareIntent)
    }
}