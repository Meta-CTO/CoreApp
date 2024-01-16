package com.metacto.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

expect class NavManager constructor() {
    fun navigate(destination: NavDestination)
    fun clearAndNavigate(destination: NavDestination)
    fun <D : NavDestination> popToExclusive(destClass: KClass<D>)
    fun <D : NavDestination> popToInclusive(destClass: KClass<D>)
    fun popByCount(popCount: Int)
    fun navigateAndPopupCurrent(destination: NavDestination)
    fun navigateToBottomSheet(destination: NavDestination)
    fun goBack()
    fun <R> sendResult(source: String?, result: R)
    suspend fun collectNavResults(callback: (NavResult<*>?) -> Unit)
    fun <R> goBackWithResult(source: String?, result: R)
    inline fun <reified S, reified R> onNavResult(
        coroutineScope: CoroutineScope,
        crossinline callback: (R) -> Unit
    )
}