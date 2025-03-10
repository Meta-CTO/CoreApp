package com.metacto.core.navigation

import kotlinx.coroutines.CompletableDeferred
import kotlin.reflect.KClass

sealed class NavEffect {
    data class NavigateTo(
        val destination: NavDestination,
        val behaviour: NavigateBehaviour
    ) : NavEffect()

    data class ClearAndNavigateTo(val destination: NavDestination) : NavEffect()

    data class PopToExclusive<D : NavDestination>(val destClass: KClass<D>) : NavEffect()

    data class PopToInclusive<D : NavDestination>(val destClass: KClass<D>) : NavEffect()

    data class PopByCount(val popCount: Int) : NavEffect()

    data class NavigateAndPopCurrent(val destination: NavDestination) : NavEffect()

    data class NavigateAndPopToExclusive<D : NavDestination>(
        val navToDest: NavDestination,
        val popToDestClass: KClass<D>
    ) : NavEffect()

    data class NavigateAndPopToInclusive<D : NavDestination>(
        val navToDest: NavDestination,
        val popToDestClass: KClass<D>
    ) : NavEffect()

    data class NavigateToBottomSheet(val destination: NavDestination) : NavEffect()

    data class CheckScreenByTag(
        val tag: String,
        val result: CompletableDeferred<Boolean>
    ) : NavEffect()

    data class CheckScreenByClass<D : NavDestination>(
        val clazz: KClass<D>,
        val result: CompletableDeferred<Boolean>
    ) : NavEffect()

    data object GoBack : NavEffect()
}