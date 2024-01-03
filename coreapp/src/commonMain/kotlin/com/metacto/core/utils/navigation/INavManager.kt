package com.metacto.core.utils.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

interface INavManager {
    fun navigate(destination: NavDestination)
    fun clearAndNavigate(destination: NavDestination)
    fun <D : NavDestination> popToExclusive(destClass: KClass<D>)
    fun <D : NavDestination> popToInclusive(destClass: KClass<D>)
    fun popByCount(popCount: Int)
    fun navigateAndPopupCurrent(destination: NavDestination)
    fun navigateToBottomSheet(destination: NavDestination)
    fun goBack()
    fun collectNavEffects(coroutineScope: CoroutineScope, callback: (NavEffect) -> Unit)
    fun <R> sendResult(source: String?, result: R)
    suspend fun collectNavResults(callback: (NavResult<*>) -> Unit)
}

inline fun <reified S, reified R> INavManager.onNavResult(
    coroutineScope: CoroutineScope,
    crossinline callback: (R) -> Unit
) {
    coroutineScope.launch {
        collectNavResults {
            if (S::class.simpleName == it.source && it.result is R) {
                callback.invoke(it.result as R)
                this.cancel()
            }
        }
    }
}