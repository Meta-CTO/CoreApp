package com.metacto.core.ui.components.containers

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.metacto.core.ui.extensions.isKeyboardVisible
import com.metacto.core.ui.extensions.rememberPrevious

@Composable
fun CoreLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    dismissKeyboardWhenScrolled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    if (dismissKeyboardWhenScrolled) {
        val currentPosition by remember { derivedStateOf { state.firstVisibleItemIndex } }
        val prevPosition = rememberPrevious(currentPosition) ?: -1
        val isKeyboardVisible by isKeyboardVisible()

        LaunchedEffect(currentPosition, prevPosition, isKeyboardVisible) {
            if (prevPosition > currentPosition && isKeyboardVisible) {
                keyboardController?.hide()
            }
        }
    }

    LazyColumn(
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        modifier = modifier,
        content = content
    )
}