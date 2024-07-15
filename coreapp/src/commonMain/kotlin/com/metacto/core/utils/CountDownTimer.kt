package com.metacto.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountDownTimer(
    seconds: Int,
    private val onTick: (Int) -> Unit,
    private val onStarted: ((Int) -> Unit)? = null,
    private val onEnded: (() -> Unit)? = null,
    private val onStopped: (() -> Unit)? = null
) {
    private var remainingSeconds = seconds
    private var job: Job? = null

    suspend fun start() {
        onStarted?.invoke(remainingSeconds)
        job = CoroutineScope(Dispatchers.Default).launch {
            tick()
        }
    }

    suspend fun stop() {
        job?.cancelAndJoin()
        onStopped?.invoke()
    }

    private suspend fun tick() {
        while (remainingSeconds > 0) {
            delay(TICK_DURATION)
            remainingSeconds--
            onTick.invoke(remainingSeconds)
        }
        onEnded?.invoke()
    }

    companion object {
        private const val TICK_DURATION = 1000L // One second
    }
}
