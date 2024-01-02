package com.metacto.core.utils

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchersProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}