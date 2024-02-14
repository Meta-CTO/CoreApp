package com.metacto.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.components.bottomSheets.BottomSheetInsetsContainer
import com.metacto.core.presentation.components.voyager.FadeTransition
import com.metacto.core.presentation.theme.CoreTheme
import org.koin.compose.rememberKoinInject


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CoreAppNavigator(
    modifier: Modifier,
    navManager: NavManager = rememberKoinInject(),
    startScreen: Screen
) {
    // Get local navigator
    var navigator: Navigator? = null
    var sheetNavigator: BottomSheetNavigator? = null

    // Handle navigation effects
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        navManager.collectNavEffects(this) { effect ->
            when (effect) {
                is NavEffect.NavigateTo -> navigator?.navigateTo(
                    screen = effect.destination,
                    behaviour = effect.behaviour
                )

                is NavEffect.ClearAndNavigateTo -> navigator?.clearAndNavigateTo(
                    screen = effect.destination
                )

                is NavEffect.PopToExclusive<*> -> navigator?.popToExclusive(
                    screenClass = effect.destClass
                )

                is NavEffect.PopToInclusive<*> -> navigator?.popToInclusive(
                    screenClass = effect.destClass
                )

                is NavEffect.PopByCount -> navigator?.popByCount(
                    popCount = effect.popCount
                )

                is NavEffect.NavigateAndPopCurrent -> navigator?.navigateAndPopCurrent(
                    screen = effect.destination
                )

                is NavEffect.NavigateToBottomSheet -> sheetNavigator?.show(
                    effect.destination
                )

                is NavEffect.GoBack -> {
                    // Hide bottom sheet if visible or navigate back
                    if (sheetNavigator?.isVisible == true) {
                        sheetNavigator?.hide()
                    } else {
                        navigator?.pop()
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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        FadeTransition(createdNavigator)
                    }
                }
            )
        }
    )
}