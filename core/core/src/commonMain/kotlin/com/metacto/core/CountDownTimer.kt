package com.metacto.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountDownTimer(
    seconds: Int,
    private val onSecondsTick: ((seconds: Int) -> Unit)? = null,
    private val onDatePartsTick: ((days: Int, hours: Int, mins: Int, seconds: Int) -> Unit)? = null,
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
            // Notify onSecondsTick
            onSecondsTick?.invoke(remainingSeconds)

            // Calculate date parts and invoke onDatePartsTick if needed
            if (onDatePartsTick != null) {
                // Calculate days, hours, minutes, and seconds
                val days = remainingSeconds / (24 * 3600)
                val hours = (remainingSeconds % (24 * 3600)) / 3600
                val minutes = (remainingSeconds % 3600) / 60
                val seconds = remainingSeconds % 60

                onDatePartsTick.invoke(days, hours, minutes, seconds)
            }

            remainingSeconds--
            delay(TICK_DURATION)
        }
        onEnded?.invoke()
    }

    companion object {
        private const val TICK_DURATION = 1000L // One second
    }
}
