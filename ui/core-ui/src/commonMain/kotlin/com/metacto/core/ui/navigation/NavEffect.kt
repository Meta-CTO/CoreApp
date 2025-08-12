package com.metacto.core.ui.navigation

import kotlinx.coroutines.CompletableDeferred
import kotlin.reflect.KClass

sealed class NavEffect {
    data class NavigateTo(
        val destination: NavScreen,
        val behaviour: NavigateBehaviour
    ) : NavEffect()

    data class ClearAndNavigateTo(val destination: NavScreen) : NavEffect()

    data class PopToExclusive<D : NavScreen>(val destClass: KClass<D>) : NavEffect()

    data class PopToInclusive<D : NavScreen>(val destClass: KClass<D>) : NavEffect()

    data class PopByCount(val popCount: Int) : NavEffect()

    data class NavigateAndPopCurrent(val destination: NavScreen) : NavEffect()

    data class NavigateAndPopToExclusive<D : NavScreen>(
        val navToDest: NavScreen,
        val popToDestClass: KClass<D>
    ) : NavEffect()

    data class NavigateAndPopToInclusive<D : NavScreen>(
        val navToDest: NavScreen,
        val popToDestClass: KClass<D>
    ) : NavEffect()

    data class NavigateToBottomSheet(val destination: NavSheet) : NavEffect()

    data class CheckScreenByTag(
        val tag: String,
        val result: CompletableDeferred<Boolean>
    ) : NavEffect()

    data class CheckScreenByClass<D : NavScreen>(
        val clazz: KClass<D>,
        val result: CompletableDeferred<Boolean>
    ) : NavEffect()

    data object GoBack : NavEffect()
}