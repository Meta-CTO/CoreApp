package com.metacto.core.ui.navigation

import cafe.adriel.voyager.navigator.Navigator
import com.metacto.core.extensions.contains
import kotlin.reflect.KClass

internal fun Navigator.navigateTo(
    screen: NavScreen,
    behaviour: NavigateBehaviour
) {
    // Prepare isSameCurrentScreen flag
    val isSameCurrentScreen = (lastItemOrNull as? NavScreen)?.screenTag == screen.screenTag

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

internal fun Navigator.clearAndNavigateTo(screen: NavScreen) {
    replaceAll(screen)
}

internal fun <D : NavScreen> Navigator.popToExclusive(screenClass: KClass<D>) {
    popUntil { it::class == screenClass }
}

internal fun <D : NavScreen> Navigator.popToInclusive(screenClass: KClass<D>) {
    val result = popUntil { it::class == screenClass }
    if (result) pop()
}

internal fun Navigator.popByCount(popCount: Int) {
    repeat(popCount) {
        pop()
    }
}

internal fun Navigator.navigateAndPopCurrent(screen: NavScreen) {
    replace(screen)
}

internal fun <D : NavScreen> Navigator.navigateAndPopToExclusive(
    navToScreen: NavScreen,
    popToScreenClass: KClass<D>
) {
    popToExclusive(popToScreenClass)
    push(navToScreen)
}

internal fun <D : NavScreen> Navigator.navigateAndPopToInclusive(
    navToScreen: NavScreen,
    popToScreenClass: KClass<D>
) {
    popToInclusive(popToScreenClass)
    push(navToScreen)
}

internal fun Navigator.checkScreenByTag(tag: String): Boolean {
    return items
        .mapNotNull { it as? NavScreen }
        .contains { it.screenTag == tag }
}

internal fun <D : NavScreen> Navigator.checkScreenByClass(clazz: KClass<D>): Boolean {
    return items
        .mapNotNull { it as? NavScreen }
        .contains { it::class == clazz }
}

internal fun Navigator.goBack() {
    pop()
}