package com.metacto.core.utils.launchers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.metacto.coreApp.MR


class IntentLauncher(private val context: Context) : IIntentLauncher {

    override fun launchEmail(
        email: String,
        subject: String?,
        body: String?
    ) = try {
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

        context.startActivity(intent)
    } catch (_: Throwable) {
        Toast.makeText(
            context,
            MR.strings.no_email_apps_found_on_your_device.resourceId,
            Toast.LENGTH_LONG
        ).show()
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

    override fun launchPhone(phone: String) = try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (t: Throwable) {
        Toast.makeText(
            context,
            MR.strings.no_phone_apps_found_on_your_device.resourceId,
            Toast.LENGTH_LONG
        ).show()
    }
}