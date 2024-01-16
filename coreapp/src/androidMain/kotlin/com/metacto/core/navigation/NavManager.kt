package com.metacto.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@OptIn(DelicateCoroutinesApi::class)
actual class NavManager {
    private val _effects: Channel<NavEffect> = Channel()
    private val effects = _effects.receiveAsFlow()
    private val _results = MutableStateFlow<NavResult<*>?>(null)

    actual fun navigate(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateTo(destination)
            )
        }
    }

    actual fun clearAndNavigate(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.ClearAndNavigateTo(destination)
            )
        }
    }

    actual fun <D : NavDestination> popToExclusive(destClass: KClass<D>) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopToExclusive(destClass)
            )
        }
    }

    actual fun <D : NavDestination> popToInclusive(destClass: KClass<D>) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopToInclusive(destClass)
            )
        }
    }

    actual fun popByCount(popCount: Int) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopByCount(popCount)
            )
        }
    }

    actual fun navigateAndPopupCurrent(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateAndPopCurrent(destination)
            )
        }
    }

    actual fun navigateToBottomSheet(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateToBottomSheet(destination)
            )
        }
    }

    actual fun goBack() {
        GlobalScope.launch {
            _effects.send(
                NavEffect.GoBack
            )
        }
    }

    fun collectNavEffects(
        coroutineScope: CoroutineScope,
        callback: (NavEffect) -> Unit
    ) {
        coroutineScope.launch {
            effects.onEach(callback).collect()
        }
    }

    actual fun <R> sendResult(source: String?, result: R) {
        GlobalScope.launch {
            val navResult = NavResult(
                source = source,
                result = result
            )

            // Push the nav result then push null value to prevent getting this value again once re-visit same screen
            _results.value = navResult
            _results.value = null
        }
    }

    actual suspend fun collectNavResults(callback: (NavResult<*>?) -> Unit) {
        _results.onEach(callback).collect()
    }

    actual fun <R> goBackWithResult(source: String?, result: R) {
        GlobalScope.launch {
            // Go back
            goBack()
            delay(50)
            // Create the nav results
            val navResult = NavResult(
                source = source,
                result = result
            )
            // Push the nav result then push null value to prevent getting this value again once re-visit same screen
            _results.value = navResult
            delay(1000)
            _results.value = null
        }
    }

    actual suspend inline fun <reified S, reified R> onNavResult(
        crossinline callback: (R) -> Unit
    ) {
        collectNavResults {
            if (it?.source == S::class.simpleName && it?.result is R) {
                callback.invoke(it?.result as R)
            }
        }
    }
}