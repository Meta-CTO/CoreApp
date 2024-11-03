package com.metacto.core.presentation.itemPicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.presentation.theme.CoreTheme.spacings
import kotlinx.coroutines.delay

private const val ANIM_DURATION = 300

private val ENTER_ANIM = slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(
        durationMillis = ANIM_DURATION,
        easing = FastOutSlowInEasing
    )
)

private val EXIT_ANIM = slideOutVertically(
    targetOffsetY = { it },
    animationSpec = tween(
        durationMillis = ANIM_DURATION,
        easing = FastOutSlowInEasing
    )
)

@Composable
internal fun ItemPickerContainer(
    modifier: Modifier = Modifier,
    items: List<PickerItem>,
    selectedItem: PickerItem?,
    onItemSelected: ((PickerItem) -> Unit)?,
    onDismissed: () -> Unit
) {
    // States
    var isPickerVisible by remember {
        mutableStateOf(items.isNotEmpty())
    }
    var currentItems by remember {
        mutableStateOf(items)
    }

    // Launched effect to update the state if required
    LaunchedEffect(items) {
        isPickerVisible = items.isNotEmpty()

        // Delay setting the items to allow the exit animation to complete
        if (items.isEmpty()) {
            delay(ANIM_DURATION.toLong())
        }
        currentItems = items
    }

    // Animation container
    AnimatedVisibility(
        visible = isPickerVisible,
        enter = ENTER_ANIM,
        exit = EXIT_ANIM,
        modifier = modifier
            .fillMaxWidth()
            .height(spacings.nativeItemPicker.pickerHeight)
    ) {
        ItemPicker(
            items = currentItems,
            selectedItem = selectedItem,
            onItemSelected = onItemSelected,
            onDismissRequested = {
                isPickerVisible = false
                onDismissed.invoke()
            }
        )
    }
}

@Composable
internal expect fun ItemPicker(
    modifier: Modifier = Modifier,
    items: List<PickerItem>,
    selectedItem: PickerItem?,
    onItemSelected: ((PickerItem) -> Unit)?,
    onDismissRequested: () -> Unit
)