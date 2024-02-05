package com.metacto.core.utils.eventBroadcaster

expect object EventBroadcaster {
    fun <T : Any> notify(event: T)

    suspend fun <T : Any> subscribeToEvent(
        eventName: String,
        onReceived: (T) -> Unit
    )
}