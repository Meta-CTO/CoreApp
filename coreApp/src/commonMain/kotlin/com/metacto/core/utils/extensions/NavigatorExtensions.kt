package com.metacto.core.utils.extensions

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import kotlin.reflect.KClass

fun Navigator.navigateTo(screen: Screen) {
    push(screen)
}

fun Navigator.clearAndNavigateTo(screen: Screen) {
    replaceAll(screen)
}

fun <D : Screen> Navigator.popToExclusive(screenClass: KClass<D>) {
    popUntil { it::class == screenClass }
}

fun <D : Screen> Navigator.popToInclusive(screenClass: KClass<D>) {
    val result = popUntil { it::class == screenClass }
    if (result) pop()
}

fun Navigator.popByCount(popCount: Int) {
    repeat(popCount) {
        pop()
    }
}

fun Navigator.navigateAndPopCurrent(screen: Screen) {
    replace(screen)
}

fun Navigator.goBack() {
    pop()
}