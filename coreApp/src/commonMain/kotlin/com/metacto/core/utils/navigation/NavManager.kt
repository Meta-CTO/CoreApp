package com.metacto.core.utils.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@OptIn(DelicateCoroutinesApi::class)
class NavManager : INavManager {
    private val _effects: Channel<NavEffect> = Channel()
    private val effects = _effects.receiveAsFlow()
    private val _results = Channel<NavResult<*>>()
    private val results = _results.receiveAsFlow()

    override fun navigate(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateTo(destination)
            )
        }
    }

    override fun clearAndNavigate(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.ClearAndNavigateTo(destination)
            )
        }
    }

    override fun <D : NavDestination> popToExclusive(destClass: KClass<D>) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopToExclusive(destClass)
            )
        }
    }

    override fun <D : NavDestination> popToInclusive(destClass: KClass<D>) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopToInclusive(destClass)
            )
        }
    }

    override fun popByCount(popCount: Int) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.PopByCount(popCount)
            )
        }
    }

    override fun navigateAndPopupCurrent(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateAndPopCurrent(destination)
            )
        }
    }

    override fun navigateToBottomSheet(destination: NavDestination) {
        GlobalScope.launch {
            _effects.send(
                NavEffect.NavigateToBottomSheet(destination)
            )
        }
    }

    override fun goBack() {
        GlobalScope.launch {
            _effects.send(
                NavEffect.GoBack
            )
        }
    }

    override fun collectNavEffects(
        coroutineScope: CoroutineScope,
        callback: (NavEffect) -> Unit
    ) {
        coroutineScope.launch {
            effects.onEach(callback).collect()
        }
    }

    override fun <R> sendResult(source: String?, result: R) {
        GlobalScope.launch {
            _results.send(
                NavResult(
                    source = source,
                    result = result
                )
            )
        }
    }

    override suspend fun collectNavResults(callback: (NavResult<*>) -> Unit) {
        results.onEach(callback).collect()
    }
}