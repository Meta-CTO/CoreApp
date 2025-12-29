package com.metacto.core.ui.launchers

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.metacto.core.extensions.openAppSettings
import com.metacto.core.ui.resources.IResourceProvider
import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.no_browser_installed
import com.metacto.core.ui.resources.no_email_apps_found_on_your_device
import com.metacto.core.ui.resources.no_phone_apps_found_on_your_device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import java.io.File
import java.net.URL
import java.util.Locale
import androidx.core.net.toUri
import com.metacto.core.date.toMillis

class IntentLauncher(
    private val context: Context,
    private val resourceProvider: IResourceProvider
) : IIntentLauncher {

    private val browserPackages = listOf(
        "com.android.chrome",
        "com.chrome.beta",
        "com.chrome.dev",
        "org.mozilla.firefox",
        "com.opera.browser",
        "com.microsoft.emmx",
        "com.brave.browser",
        "com.samsung.android.app.sbrowser"
    )

    override fun launchEmail(
        email: String,
        subject: String?,
        body: String?
    ) = try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
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

    override fun launchAppInStore(appId: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = "market://details?id=$appId".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(
                intent
            )
        } catch (e: ActivityNotFoundException) {
            launchBrowser("https://play.google.com/store/apps/details?id=$appId")
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
            data = "tel:$phone".toUri()
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

    override fun launchBrowser(url: String, onError: (() -> Unit)?) {
        val pm = context.packageManager
        val uri = url.toUri()
        tryKnownBrowsers(pm, uri, onError)
    }

    private fun tryKnownBrowsers(pm: PackageManager, uri: Uri, onError: (() -> Unit)?) {
        for (packageName in browserPackages) {
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage(packageName)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            if (intent.resolveActivity(pm) != null) {
                try {
                    context.startActivity(intent)
                    return // Success, stop
                } catch (_: Throwable) {
                    // Try next browser
                }
            }
        }
        tryGenericBrowserIntent(pm, uri, onError)
    }

    private fun tryGenericBrowserIntent(pm: PackageManager, uri: Uri, onError: (() -> Unit)?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Throwable) {
            tryQueryIntentActivities(pm, uri, onError)
        }
    }

    private fun tryQueryIntentActivities(pm: PackageManager, uri: Uri, onError: (() -> Unit)?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

            for (resolveInfo in resolveInfos) {
                val browserIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                    setPackage(resolveInfo.activityInfo.packageName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                if (browserIntent.resolveActivity(pm) != null) {
                    try {
                        context.startActivity(browserIntent)
                        return // Success, stop
                    } catch (_: Throwable) {
                        // Try next resolveInfo
                    }
                }
            }
        } catch (_: Throwable) {
            // queryIntentActivities threw
        }
        handleBrowserLaunchError(onError)
    }

    private fun handleBrowserLaunchError(onError: (() -> Unit)?) {
        if (onError != null) {
            onError()
        } else {
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

    override fun launchMap(latitude: Double, longitude: Double, name: String?) {
        try {
            // Attempt to launch a geo intent
            val mapIntentUri = String.format(
                Locale.ENGLISH,
                "geo:0,0?q=%f,%f(%s)",
                latitude,
                longitude,
                Uri.encode(name)
            ).toUri()
            val intent = Intent(Intent.ACTION_VIEW, mapIntentUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)

        } catch (_: Throwable) {
            val mapUrl = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?q=loc:%f,%f(%s)",
                latitude,
                longitude,
                Uri.encode(name)
            )

            // Launch browser
            launchBrowser(mapUrl)
        }
    }

    override fun checkAppInstalled(appId: String): Boolean {
        val packageManager = context.applicationContext.packageManager
        return try {
            packageManager.getPackageInfo(appId, 0)
            true
        } catch (e: Throwable) {
            false
        }
    }

    override fun openDeepLink(link: String, onError: (() -> Unit)?): Boolean {
        try {
            val linkIntent = Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                data = link.toUri()
            }
            context.startActivity(linkIntent)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            onError?.invoke()
            return false
        }
    }

    override fun canHandleScheme(scheme: String, host: String?): Boolean {
        val uriBuilder = Uri.Builder().scheme(scheme)
        host?.let { uriBuilder.authority(it) }

        val intent = Intent(Intent.ACTION_VIEW, uriBuilder.build())
        return intent.resolveActivity(context.packageManager) != null
    }

    override suspend fun shareImage(imageUrl: String, text: String?) = withContext(Dispatchers.IO) {
        // Create the image file
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val imageFile = File(cachePath, "image.png")

        // Download the image and get the uri
        val inputStream = URL(imageUrl).openStream()
        imageFile.outputStream().use { inputStream.copyTo(it) }
        val imageUri =
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)

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
            data = "content://com.android.calendar/events".toUri()
            putExtra(Events.TITLE, eventTitle)
            putExtra(Events.DESCRIPTION, eventDescription)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartTime.toMillis())
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eventEndTime.toMillis())
            putExtra(Events.ALL_DAY, false)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }
}
