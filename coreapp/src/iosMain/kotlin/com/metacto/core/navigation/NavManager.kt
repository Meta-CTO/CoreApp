package com.metacto.core.navigation

import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.metacto.core.presentation.base.onDispose
import com.metacto.core.utils.extensions.clearAndNavigateTo
import com.metacto.core.utils.extensions.navigateAndPopCurrent
import com.metacto.core.utils.extensions.navigateTo
import com.metacto.core.utils.extensions.popByCount
import com.metacto.core.utils.extensions.popToExclusive
import com.metacto.core.utils.extensions.popToInclusive
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@OptIn(DelicateCoroutinesApi::class)
actual class NavManager {
    private var navigator: Navigator? = null
    private var sheetNavigator: BottomSheetNavigator? = null
    private val navStack = ArrayDeque<NavDestination>()

    private val _results = MutableStateFlow<NavResult<*>?>(null)

    fun onNavigatorCreated(navigator: Navigator) {
        // Set current navigator if only null
        // Otherwise we discard the new created navigator to fix navigation is restarted in iOS when
        // navigate to native UIViewController and return back to compose controller
        if (this.navigator == null) {
            this.navigator = navigator
        }
    }

    fun getNavigator() = navigator

    fun onSheetNavigatorCreated(sheetNavigator: BottomSheetNavigator) {
        // Update current sheet navigator
        this.sheetNavigator = sheetNavigator
    }

    actual fun navigate(destination: NavDestination) {
        navigator?.navigateTo(destination)
        navStack.addLast(destination)
    }

    actual fun clearAndNavigate(destination: NavDestination) {
        // Pop from the nav stack
        while (navStack.isNotEmpty()) {
            navStack.removeLastOrNull()?.onDispose()
        }

        // Navigate
        navigator?.clearAndNavigateTo(destination)

        // Then add new destination to the stack
        navStack.addLast(destination)
    }

    actual fun <D : NavDestination> popToExclusive(destClass: KClass<D>) {
        // Pop from the nav stack
        while (navStack.isNotEmpty() && navStack.last()::class != destClass) {
            val poppedDestination = navStack.removeLastOrNull()
            poppedDestination?.onDispose()
        }

        // Then pop in the navigator
        navigator?.popToExclusive(
            screenClass = destClass
        )
    }

    actual fun <D : NavDestination> popToInclusive(destClass: KClass<D>) {
        // Pop from the nav stack
        while (navStack.isNotEmpty() && navStack.last()::class != destClass) {
            val poppedDestination = navStack.removeLastOrNull()
            poppedDestination?.onDispose()
        }
        navStack.removeLastOrNull()?.onDispose()

        // Then pop in the navigator
        navigator?.popToInclusive(
            screenClass = destClass
        )
    }

    actual fun popByCount(popCount: Int) {
        // Pop from the nav stack
        repeat(popCount) {
            val poppedDestination = navStack.removeLastOrNull() ?: return@repeat
            poppedDestination.onDispose()
        }

        // Then pop in the navigator
        navigator?.popByCount(
            popCount = popCount
        )
    }

    actual fun navigateAndPopupCurrent(destination: NavDestination) {
        // Navigate
        navigator?.navigateAndPopCurrent(
            screen = destination
        )

        // Pop from the nav stack
        val poppedDestination = navStack.removeLastOrNull()
        poppedDestination?.onDispose()

        // Then add the new destination to the stack
        navStack.addLast(destination)
    }

    actual fun navigateToBottomSheet(destination: NavDestination) {
        sheetNavigator?.show(
            screen = destination
        )
    }

    actual fun goBack() {
        // Hide bottom sheet if visible or navigate back
        if (sheetNavigator?.isVisible == true) {
            sheetNavigator?.hide()
        } else {
            // Pop from the nav stack
            val poppedDestination = navStack.removeLastOrNull()
            poppedDestination?.onDispose()

            // Then pop in the navigator
            navigator?.pop()
        }
    }

    actual fun <R> sendResult(source: String?, result: R) {
        GlobalScope.launch {
            val navResult = NavResult(
                source = source,
                result = result
            )

            // Push the nav result then push null value to prevent getting this value again once re-visit same screen
            _results.value = navResult
            _results.value = null
        }
    }

    actual suspend fun collectNavResults(callback: (NavResult<*>?) -> Unit) {
        _results.onEach(callback).collect()
    }

    actual fun <R> goBackWithResult(source: String?, result: R) {
        GlobalScope.launch {
            // Go back
            goBack()
            delay(50)
            // Create the nav results
            val navResult = NavResult(
                source = source,
                result = result
            )
            // Push the nav result then push null value to prevent getting this value again once re-visit same screen
            _results.value = navResult
            delay(1000)
            _results.value = null
        }
    }

    actual suspend inline fun <reified S, reified R> onNavResult(
        crossinline callback: (R) -> Unit
    ) {
        collectNavResults {
            if (it?.source == S::class.simpleName && it?.result is R) {
                callback.invoke(it?.result as R)
            }
        }
    }
}