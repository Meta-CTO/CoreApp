package com.metacto.core.utils.launchers

import com.metacto.core.utils.delegates.EventEditDelegate
import com.metacto.core.utils.extensions.openAppSettings
import com.metacto.core.utils.extensions.runOnMainThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.EventKit.EKEntityType
import platform.EventKit.EKEvent
import platform.EventKit.EKEventStore
import platform.EventKitUI.EKEventEditViewController
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage

class IntentLauncher : IIntentLauncher {

    override fun launchEmail(email: String, subject: String?, body: String?) = runOnMainThread {
        val url = "mailto:$email?subject=${subject.orEmpty()}&body=${body.orEmpty()}"
        openUrl(url)
    }

    override fun launchStore(appId: String) = runOnMainThread {
        // Open the url
        val url = "itms-apps://itunes.apple.com/app/$appId"
        val canOpen = openUrl(url)

        // Open browser if couldn't be opened
        if (canOpen.not()) {
            launchBrowser("https://apps.apple.com/app/$appId")
        }
    }

    override fun shareText(text: String) = runOnMainThread {
        // Get and validate the root view controller
        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController
            ?: return@runOnMainThread

        // Create the activity controller
        val activityController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        // Then present it
        rootViewController.presentViewController(
            viewControllerToPresent = activityController,
            animated = true,
            completion = null
        )
    }

    override fun launchPhone(phone: String) = runOnMainThread {
        val url = "tel://$phone"
        openUrl(url)
    }

    override fun launchBrowser(url: String) = runOnMainThread {
        openUrl(url)
    }

    override fun launchAppSettings() {
        openAppSettings()
    }

    override suspend fun shareImage(imageUrl: String, text: String?) = withContext(Dispatchers.IO) {
        // Create and validate the UIImage
        val nsUrl = requireNotNull(NSURL.URLWithString(imageUrl)) {
            "Failed to download the image"
        }
        val data = requireNotNull(NSData.dataWithContentsOfURL(nsUrl)) {
            "Failed to download the image"
        }
        val image = UIImage.imageWithData(data)

        // Get and validate the root view controller
        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController
            ?: return@withContext

        // Create the activity controller
        val activityController = UIActivityViewController(
            activityItems = listOfNotNull(image, text),
            applicationActivities = null
        )

        // Then present it
        withContext(Dispatchers.Main) {
            rootViewController.presentViewController(
                viewControllerToPresent = activityController,
                animated = true,
                completion = null
            )
        }
    }

    override fun addEventToCalendar(
        eventTitle: String,
        eventDescription: String,
        eventStartTime: LocalDateTime,
        eventEndTime: LocalDateTime
    ) {
        val eventStore = EKEventStore()
        eventStore.requestAccessToEntityType(EKEntityType.EKEntityTypeEvent) { granted, error ->
            if (granted) {
                val event = EKEvent.eventWithEventStore(eventStore).apply {
                    this.title = eventTitle
                    this.notes = eventDescription
                    this.startDate = eventStartTime.toNSDateComponents().date
                    this.endDate = eventEndTime.toNSDateComponents().date
                    this.calendar = eventStore.defaultCalendarForNewEvents
                }

                runOnMainThread {
                    // Get and validate the root view controller
                    val rootViewController = UIApplication.sharedApplication
                        .keyWindow
                        ?.rootViewController
                        ?: return@runOnMainThread

                    val eventController = EKEventEditViewController().apply {
                        this.event = event
                        this.eventStore = eventStore
                        this.editViewDelegate = EventEditDelegate()
                    }

                    rootViewController.presentViewController(
                        eventController,
                        animated = true,
                        completion = null
                    )
                }
            } else {
                // Handle access denial or error
                println("Access denied or error: ${error?.localizedDescription}")
            }
        }
    }

    private fun openUrl(url: String): Boolean {
        // Create the url
        val nsUrl = NSURL.URLWithString(url) ?: return false

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(nsUrl).not()) return false

        // Then open it
        runOnMainThread {
            UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any?>(), null)
        }
        return true
    }
}
