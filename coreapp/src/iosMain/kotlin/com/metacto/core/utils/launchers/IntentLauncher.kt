package com.metacto.core.utils.launchers

import com.metacto.core.utils.delegates.EventEditDelegate
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
    override fun launchEmail(email: String, subject: String?, body: String?) {
        // Create the url
        val urlString = "mailto:$email?subject=${subject.orEmpty()}&body=${body.orEmpty()}"
        val url = NSURL.URLWithString(urlString) ?: return

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(url).not()) return

        // Then open it
        UIApplication.sharedApplication.openURL(url)
    }

    override fun launchStore(appId: String) {
        // Create the url
        val urlString = "itms-apps://itunes.apple.com/app/$appId"
        val url = NSURL.URLWithString(urlString) ?: return

        // Check if can open url
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            // Then open it
            UIApplication.sharedApplication.openURL(url)
        } else {
            launchBrowser("https://apps.apple.com/app/$appId")
        }
    }

    override fun shareText(text: String) {
        // Get and validate the root view controller
        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController
            ?: return

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

    override fun launchPhone(phone: String) {
        // Create the url
        val urlString = "tel://$phone"
        val url = NSURL.URLWithString(urlString) ?: return

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(url).not()) return

        // Then open it
        UIApplication.sharedApplication.openURL(url)
    }

    override fun launchBrowser(url: String) {
        // Create the url
        val nsUrl = NSURL.URLWithString(url) ?: return

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(nsUrl).not()) return

        // Then open it
        UIApplication.sharedApplication.openURL(nsUrl)
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
    ) = runOnMainThread {
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

                // Get and validate the root view controller
                val rootViewController = UIApplication.sharedApplication
                    .keyWindow
                    ?.rootViewController
                    ?: return@requestAccessToEntityType

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
            } else {
                // Handle access denial or error
                println("Access denied or error: ${error?.localizedDescription}")
            }
        }
    }
}
