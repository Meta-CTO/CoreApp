package com.metacto.core.navigation

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

    data class NavigateToBottomSheet(val destination: NavDestination) : NavEffect()

    data object GoBack : NavEffect()
}