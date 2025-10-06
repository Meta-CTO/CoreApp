package com.metacto.catalogapp.presentation.main.applepay.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.metacto.catalogapp.presentation.app.viewsFactory.IViewsFactory
import com.metacto.catalogapp.presentation.app.viewsFactory.UIViewType
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun ApplePayButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    val viewsFactory = koinInject<IViewsFactory>()
    val uiView = remember {
        viewsFactory.create(
            UIViewType.ApplePayButton(
                onClick = onClick
            )
        )
    }

    UIKitView(
        modifier = modifier,
        factory = { uiView },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        )
    )
}