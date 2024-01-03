package com.metacto.core.utils

import kotlinx.coroutines.delay

class CountDownTimer(
    seconds: Int,
    private val onTick: (Int) -> Unit,
    private val onStarted: ((Int) -> Unit)? = null,
    private val onEnded: (() -> Unit)? = null
) {
    private var remainingSeconds = seconds

    suspend fun start() {
        onStarted?.invoke(remainingSeconds)
        tick()
    }

    private suspend fun tick() {
        delay(TICK_DURATION)

        remainingSeconds--
        onTick.invoke(remainingSeconds)

        if (remainingSeconds == 0) {
            onEnded?.invoke()
        } else {
            tick()
        }
    }

    companion object {
        private const val TICK_DURATION = 1000L // One second
    }
}