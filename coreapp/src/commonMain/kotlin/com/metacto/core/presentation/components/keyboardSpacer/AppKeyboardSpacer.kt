package com.metacto.core.presentation.components.keyboardSpacer

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.globalState.ICoreGlobalState
import org.koin.compose.rememberKoinInject

@Composable
fun AppKeyboardSpacer(
    removedHeight: Dp = 0.dp
) {
    val globalState = rememberKoinInject<ICoreGlobalState>()

    // Get ios keyboard height
    val iOSKeyboardHeight = animateDpAsState(
        targetValue = globalState.iOSKeyboardHeight.value,
        animationSpec = tween(
            durationMillis = 250,
            easing = LinearOutSlowInEasing
        )
    ).value

    // Prepare spacer height
    val spacerHeight = iOSKeyboardHeight
        .minus(removedHeight)
        .takeIf { it > 0.dp }
        ?: 0.dp

    // Then render spacer
    Spacer(
        modifier = Modifier.height(spacerHeight)
    )
}