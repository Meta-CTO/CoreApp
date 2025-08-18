package com.metacto.core.eventBroadcaster

val Class = EventBroadcaster::class

expect object EventBroadcaster {
    fun <T : Any> notify(event: T)

    suspend fun <T : Any> subscribeToEvent(
        eventName: String,
        onReceived: (T) -> Unit
    )
}