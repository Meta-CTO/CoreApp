package com.metacto.core.presentation.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.animateAlignmentAsState
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun SwitchButton(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    btnToggled: Color = CoreTheme.colors.switchBtnToggled,
    btnNonToggled: Color = CoreTheme.colors.switchBtnNonToggled,
    thumbToggled: Color = CoreTheme.colors.switchThumbToggled,
    thumbNonToggled: Color = CoreTheme.colors.switchThumbNonToggled,
    width: Dp = CoreTheme.spacings.switchBtnWidth,
    height: Dp = CoreTheme.spacings.switchBtnHeight,
) {
    // Prepare states
    val switchColor by animateColorAsState(
        targetValue = if (isChecked) btnToggled else btnNonToggled,
        label = "Switch Color"
    )
    val thumbColor by animateColorAsState(
        targetValue = if (isChecked) thumbToggled else thumbNonToggled,
        label = "Thumb Color"
    )
    val thumbAlign by animateAlignmentAsState(
        if (isChecked) 1f else -1f
    )

    // Draw the switch bg box
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(shape = CircleShape)
            .background(switchColor)
            .noRippleClickable {
                onCheckChanged.invoke(!isChecked)
            }
    ) {
        // Thumb icon
        Icon(
            imageVector = Icons.Filled.Circle,
            tint = thumbColor,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .align(thumbAlign)
        )
    }
}