package com.metacto.core.utils.launchers

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.metacto.core.utils.extensions.openAppSettings
import com.metacto.core.utils.resources.IResourceProvider
import com.metacto.coreApp.resources.*
import com.metacto.strapikmm.util.toEpochMilliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import java.io.File
import java.net.URL

class IntentLauncher(
    private val context: Context,
    private val resourceProvider: IResourceProvider
) : IIntentLauncher {

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
            resourceProvider.getString(Res.string.no_email_apps_found_on_your_device),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun launchStore(appId: String) {
        val packageName = context.packageName
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=$packageName")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(
                intent
            )
        } catch (e: ActivityNotFoundException) {
            launchBrowser("https://play.google.com/store/apps/details?id=$packageName")
        }
    }

    override fun shareText(text: String) {
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
            resourceProvider.getString(Res.string.no_phone_apps_found_on_your_device),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun launchBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Throwable) {
            Toast.makeText(
                context,
                resourceProvider.getString(Res.string.no_browser_installed),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun launchAppSettings() {
        context.openAppSettings()
    }

    override suspend fun shareImage(imageUrl: String, text: String?) = withContext(Dispatchers.IO) {
        // Create the image file
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val imageFile = File(cachePath, "image.png")

        // Download the image and get the uri
        val inputStream = URL(imageUrl).openStream()
        imageFile.outputStream().use { inputStream.copyTo(it) }
        val imageUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)

        // Create the share intent
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            text?.let { putExtra(Intent.EXTRA_TEXT, it) }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Create them chooser intent
        val chooserIntent = Intent.createChooser(shareIntent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        // Launch the chooser intent
        ContextCompat.startActivity(context, chooserIntent, null)
    }

    override fun addEventToCalendar(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: LocalDateTime,
        eventEndTime: LocalDateTime
    ) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = Uri.parse("content://com.android.calendar/events")
            putExtra(Events.TITLE, eventTitle)
            putExtra(Events.DESCRIPTION, eventDescription)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartTime.toEpochMilliseconds())
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eventEndTime.toEpochMilliseconds())
            putExtra(Events.ALL_DAY, false)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }
}