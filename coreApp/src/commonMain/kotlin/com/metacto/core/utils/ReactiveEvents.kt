@file:OptIn(DelicateCoroutinesApi::class)

package com.metacto.core.utils

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class ReactiveEvents {
    private val _events = MutableSharedFlow<AppEvent>()
    private val events = _events.asSharedFlow()

    fun invokeEvent(event: AppEvent) {
        GlobalScope.launch {
            _events.emit(event)
        }
    }

    suspend fun subscribeToEvent(
        event: AppEvent,
        onReceived: () -> Unit
    ) = events.filter { appEvent -> appEvent == event }.collectLatest { onReceived.invoke() }
}

open class AppEvent
