package com.metacto.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.rememberKoinInject


@Composable
expect fun CoreAppNavigator(
    modifier: Modifier = Modifier,
    navManager: NavManager = rememberKoinInject(),
    startScreen: Screen
)