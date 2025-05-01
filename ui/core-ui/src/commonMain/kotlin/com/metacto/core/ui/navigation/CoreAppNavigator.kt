package com.metacto.core.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.metacto.core.extensions.orFalse
import com.metacto.core.ui.CoreUIConfigs
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.components.bottomSheets.BottomSheetInsetsContainer
import com.metacto.core.ui.components.voyager.FadeTransition
import com.metacto.core.ui.extensions.onEdgeSwipe
import com.metacto.core.ui.globalState.ICoreGlobalState
import com.metacto.core.ui.theme.CoreTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import kotlin.time.Duration.Companion.milliseconds

private val NAV_BETWEEN_SHEETS_DELAY = 100.milliseconds

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun CoreAppNavigator(
    modifier: Modifier,
    navManager: NavManager = koinInject(),
    startScreen: Screen
) {
    // Get main objects
    val globalState = koinInject<ICoreGlobalState>()
    val coroutineScope = rememberCoroutineScope()
    var navigator by remember { mutableStateOf<Navigator?>(null) }
    var sheetNavigator by remember { mutableStateOf<BottomSheetNavigator?>(null) }

    // Prepare swipe objects
    val coreUIConfigs = koinInject<CoreUIConfigs>()
    val enableSwipeToGoBack by remember(coreUIConfigs, navigator?.lastItem) {
        val currentScreen = navigator?.lastItem as? BaseScreen<*>
        derivedStateOf {
            coreUIConfigs.enableSwipeToGoBack && currentScreen?.enableSwipeToGoBack == true
        }
    }

    // Handle navigation effects
    LaunchedEffect(navigator, sheetNavigator) {
        navManager.collectNavEffects(this) { effect ->
            // First dismiss the keyboard for better UX
            globalState.dismissKeyboard()

            // Then execute the navigation
            when (effect) {
                is NavEffect.NavigateTo -> navigator?.navigateTo(
                    screen = effect.destination,
                    behaviour = effect.behaviour
                )

                is NavEffect.ClearAndNavigateTo -> {
                    sheetNavigator?.hide()
                    navigator?.clearAndNavigateTo(
                        screen = effect.destination
                    )
                }

                is NavEffect.PopToExclusive<*> -> {
                    sheetNavigator?.hide()
                    navigator?.popToExclusive(
                        screenClass = effect.destClass
                    )
                }

                is NavEffect.PopToInclusive<*> -> {
                    sheetNavigator?.hide()
                    navigator?.popToInclusive(
                        screenClass = effect.destClass
                    )
                }

                is NavEffect.PopByCount -> {
                    sheetNavigator?.hide()
                    navigator?.popByCount(
                        popCount = effect.popCount
                    )
                }

                is NavEffect.NavigateAndPopCurrent -> {
                    sheetNavigator?.hide()
                    navigator?.navigateAndPopCurrent(
                        screen = effect.destination
                    )
                }

                is NavEffect.NavigateAndPopToExclusive<*> -> {
                    sheetNavigator?.hide()
                    navigator?.navigateAndPopToExclusive(
                        navToScreen = effect.navToDest,
                        popToScreenClass = effect.popToDestClass
                    )
                }

                is NavEffect.NavigateAndPopToInclusive<*> -> {
                    sheetNavigator?.hide()
                    navigator?.navigateAndPopToInclusive(
                        navToScreen = effect.navToDest,
                        popToScreenClass = effect.popToDestClass
                    )
                }

                is NavEffect.NavigateToBottomSheet -> {
                    coroutineScope.launch {
                        // Check if we already have a bottom sheet visible
                        if (sheetNavigator?.isVisible == true) {
                            // We should hide current sheet first, wait some seconds
                            sheetNavigator?.hide()
                            delay(NAV_BETWEEN_SHEETS_DELAY)
                        }

                        // Then navigate to the other sheet
                        sheetNavigator?.show(effect.destination)
                    }
                }

                is NavEffect.CheckScreenByTag -> {
                    val hasScreen = navigator?.checkScreenByTag(effect.tag).orFalse()
                    effect.result.complete(hasScreen)
                }

                is NavEffect.CheckScreenByClass<*> -> {
                    val hasScreen = navigator?.checkScreenByClass(effect.clazz).orFalse()
                    effect.result.complete(hasScreen)
                }

                is NavEffect.GoBack -> {
                    // Hide bottom sheet if visible or navigate back
                    if (sheetNavigator?.isVisible == true) {
                        sheetNavigator?.hide()
                    } else {
                        navigator?.goBack()
                    }
                }
            }
        }
    }

    BottomSheetNavigator(
        modifier = modifier.fillMaxSize(),
        sheetShape = CoreTheme.shapes.sheet,
        sheetElevation = CoreTheme.spacings.sheetElevation,
        sheetBackgroundColor = CoreTheme.colors.sheetBackground,
        skipHalfExpanded = true,
        sheetContent = {
            // Render current bottom sheet inside safe insets container
            BottomSheetInsetsContainer {
                CurrentScreen()
            }
        },
        content = { createdSheetNavigator ->
            // Update current sheet navigator navigator to the created one
            sheetNavigator = createdSheetNavigator

            // Render app navigator
            Navigator(
                screen = startScreen,
                onBackPressed = {
                    if (it is BaseScreen<*>) {
                        it.onBackPressed()
                        false
                    } else {
                        true
                    }
                },
                content = { createdNavigator ->
                    // Update current navigator to the created one
                    navigator = createdNavigator

                    // Render current screen
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .onEdgeSwipe(isEnabled = enableSwipeToGoBack) {
                                navManager.goBack()
                            }
                    ) {
                        FadeTransition(createdNavigator)
                    }
                }
            )
        }
    )
}