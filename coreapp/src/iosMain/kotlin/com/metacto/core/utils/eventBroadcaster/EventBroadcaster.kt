package com.metacto.core.utils.eventBroadcaster

import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue.Companion.mainQueue

actual object EventBroadcaster {

    actual fun <T : Any> notify(event: T) {
        NSNotificationCenter.defaultCenter.postNotificationName(
            event::class.simpleName,
            null,
            mapOf("event" to event)
        )
    }


    @Suppress("UNCHECKED_CAST")
    actual suspend fun <T : Any> subscribeToEvent(
        eventName: String,
        onReceived: (T) -> Unit
    ) {
        NSNotificationCenter.defaultCenter.addObserverForName(eventName, null, mainQueue) {
            (it?.userInfo?.get("event") as? T)?.let { event ->
                onReceived(event)
            }
        }
    }
}