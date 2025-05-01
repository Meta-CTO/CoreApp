package com.metacto.core.ui.navigation

import kotlinx.coroutines.CompletableDeferred
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

val Class = NavManager::class

@OptIn(DelicateCoroutinesApi::class)
class NavManager {
    private val _effects: Channel<NavEffect> = Channel()
    private val effects = _effects.receiveAsFlow()
    private val _results = MutableStateFlow<NavResult<*>?>(null)

    fun navigate(
        destination: NavDestination,
        behaviour: NavigateBehaviour = NavigateBehaviour.Normal
    ) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateTo(
                    destination = destination,
                    behaviour = behaviour
                )
            )
        }
    }

    fun clearAndNavigate(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.ClearAndNavigateTo(destination)
            )
        }
    }

    fun <D : NavDestination> popToExclusive(destClass: KClass<D>) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopToExclusive(destClass)
            )
        }
    }

    fun <D : NavDestination> popToInclusive(destClass: KClass<D>) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopToInclusive(destClass)
            )
        }
    }

    fun popByCount(popCount: Int) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopByCount(popCount)
            )
        }
    }

    fun navigateAndPopupCurrent(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateAndPopCurrent(destination)
            )
        }
    }

    fun <D : NavDestination> navigateAndPopToExclusive(
        navToDest: NavDestination,
        popToDestClass: KClass<D>
    ) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateAndPopToExclusive(
                    navToDest = navToDest,
                    popToDestClass = popToDestClass
                )
            )
        }
    }

    fun <D : NavDestination> navigateAndPopToInclusive(
        navToDest: NavDestination,
        popToDestClass: KClass<D>
    ) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateAndPopToInclusive(
                    navToDest = navToDest,
                    popToDestClass = popToDestClass
                )
            )
        }
    }

    fun navigateToBottomSheet(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateToBottomSheet(destination)
            )
        }
    }

    suspend fun checkScreenByTag(tag: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        _effects.send(
            NavEffect.CheckScreenByTag(
                tag = tag,
                result = result
            )
        )
        return result.await()
    }

    suspend fun <D : NavDestination> checkScreenByClass(clazz: KClass<D>): Boolean {
        val result = CompletableDeferred<Boolean>()
        _effects.send(
            NavEffect.CheckScreenByClass(
                clazz = clazz,
                result = result
            )
        )
        return result.await()
    }

    fun goBack() {
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

    fun <R> sendResult(source: String?, result: R) {
        GlobalScope.launch {
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

    suspend fun collectNavResults(callback: (NavResult<*>?) -> Unit) {
        _results.onEach(callback).collect()
    }

    fun <R> goBackWithResult(source: String?, result: R) {
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

    suspend inline fun <reified S, reified R> onNavResult(
        crossinline callback: (R) -> Unit
    ) {
        collectNavResults {
            if (it?.source == S::class.simpleName && it?.result is R) {
                callback.invoke(it?.result as R)
            }
        }
    }
}