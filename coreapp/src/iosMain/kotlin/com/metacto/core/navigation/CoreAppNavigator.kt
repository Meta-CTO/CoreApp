package com.metacto.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.components.bottomSheets.BottomSheetInsetsContainer
import com.metacto.core.presentation.components.voyager.FadeTransition
import com.metacto.core.presentation.theme.CoreTheme

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
actual fun CoreAppNavigator(
    modifier: Modifier,
    navManager: NavManager,
    startScreen: Screen
) {
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
        content = { sheetNavigator ->
            // Notify nav manager
            navManager.onSheetNavigatorCreated(sheetNavigator)

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
                content = { navigator ->
                    // Notify nav manager
                    navManager.onNavigatorCreated(navigator)

                    // Then render using the navigator in the nav manager if possible
                    navManager.getNavigator()?.let { currentNavigator ->
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            FadeTransition(currentNavigator)
                        }
                    }
                }
            )
        }
    )
}