package com.metacto.core.navigation

import cafe.adriel.voyager.navigator.Navigator
import kotlin.reflect.KClass

fun Navigator.navigateTo(
    screen: NavDestination,
    behaviour: NavigateBehaviour
) {
    // Prepare isSameCurrentScreen flag
    val isSameCurrentScreen = (lastItemOrNull as? NavDestination)?.screenTag == screen.screenTag

    // Handle navigation according to behavior
    when(behaviour) {
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

fun Navigator.clearAndNavigateTo(screen: NavDestination) {
    replaceAll(screen)
}

fun <D : NavDestination> Navigator.popToExclusive(screenClass: KClass<D>) {
    popUntil { it::class == screenClass }
}

fun <D : NavDestination> Navigator.popToInclusive(screenClass: KClass<D>) {
    val result = popUntil { it::class == screenClass }
    if (result) pop()
}

fun Navigator.popByCount(popCount: Int) {
    repeat(popCount) {
        pop()
    }
}

fun Navigator.navigateAndPopCurrent(screen: NavDestination) {
    replace(screen)
}

fun Navigator.goBack() {
    pop()
}