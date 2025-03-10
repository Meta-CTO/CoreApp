package com.metacto.core.navigation

import cafe.adriel.voyager.navigator.Navigator
import com.metacto.core.utils.extensions.contains
import kotlin.reflect.KClass

internal fun Navigator.navigateTo(
    screen: NavDestination,
    behaviour: NavigateBehaviour
) {
    // Prepare isSameCurrentScreen flag
    val isSameCurrentScreen = (lastItemOrNull as? NavDestination)?.screenTag == screen.screenTag

    // Handle navigation according to behavior
    when (behaviour) {
        NavigateBehaviour.Normal -> {
            push(screen)
        }

        NavigateBehaviour.ReplaceIfCurrent -> {
            if (isSameCurrentScreen) {
                replace(screen)
            } else {
                push(screen)
            }
        }

        NavigateBehaviour.KeepIfCurrent -> {
            if (isSameCurrentScreen.not()) {
                push(screen)
            }
        }
    }
}

internal fun Navigator.clearAndNavigateTo(screen: NavDestination) {
    replaceAll(screen)
}

internal fun <D : NavDestination> Navigator.popToExclusive(screenClass: KClass<D>) {
    popUntil { it::class == screenClass }
}

internal fun <D : NavDestination> Navigator.popToInclusive(screenClass: KClass<D>) {
    val result = popUntil { it::class == screenClass }
    if (result) pop()
}

internal fun Navigator.popByCount(popCount: Int) {
    repeat(popCount) {
        pop()
    }
}

internal fun Navigator.navigateAndPopCurrent(screen: NavDestination) {
    replace(screen)
}

internal fun <D : NavDestination> Navigator.navigateAndPopToExclusive(
    navToScreen: NavDestination,
    popToScreenClass: KClass<D>
) {
    popToExclusive(popToScreenClass)
    push(navToScreen)
}

internal fun <D : NavDestination> Navigator.navigateAndPopToInclusive(
    navToScreen: NavDestination,
    popToScreenClass: KClass<D>
) {
    popToInclusive(popToScreenClass)
    push(navToScreen)
}

internal fun Navigator.checkScreenByTag(tag: String): Boolean {
    return items
        .mapNotNull { it as? NavDestination }
        .contains { it.screenTag == tag }
}

internal fun <D : NavDestination> Navigator.checkScreenByClass(clazz: KClass<D>): Boolean {
    return items
        .mapNotNull { it as? NavDestination }
        .contains { it::class == clazz }
}

internal fun Navigator.goBack() {
    pop()
}