package com.metacto.core.utils.launchers

import android.content.Context
import android.content.Intent
import android.net.Uri

class IntentLauncher(private val context: Context) : IIntentLauncher {

    override fun launchEmail(
        email: String,
        subject: String?,
        body: String?
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK

            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            subject?.let {
                putExtra(Intent.EXTRA_SUBJECT, it)
            }
            body?.let {
                putExtra(Intent.EXTRA_TEXT, it)
            }
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    override fun launchShareText(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(shareIntent)
    }
}