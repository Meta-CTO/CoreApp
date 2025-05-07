package com.metacto.core.presentation.components.videoPlayer

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

internal sealed class VideoPlayerEvent {
    data object StoppedPip : VideoPlayerEvent()
    data object StartedPip : VideoPlayerEvent()
    data object ActivityFinished : VideoPlayerEvent()
    data class PlayerStarted(val playerId: String) : VideoPlayerEvent()
}

internal object VideoPlayerEventBroadcaster {
    private val events = MutableSharedFlow<VideoPlayerEvent>()
    private var pipActivePlayerId: String? = null

    fun getPipActivePlayerId(): String? = pipActivePlayerId
    fun setPipActivePlayerId(id: String?) { pipActivePlayerId = id }

    @OptIn(DelicateCoroutinesApi::class)
    fun emit(event: VideoPlayerEvent) {
        GlobalScope.launch { events.emit(event) }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    inline fun <reified T : VideoPlayerEvent> collectInCompose(crossinline onReceived: (T) -> Unit) {
        LaunchedEffect(Unit) {
            events.filter { it is T }.collectLatest { onReceived(it as T) }
        }
    }
}