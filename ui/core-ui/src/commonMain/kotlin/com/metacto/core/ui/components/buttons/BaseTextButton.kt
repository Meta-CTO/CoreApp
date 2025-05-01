package com.metacto.core.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.extensions.noRippleClickable
import com.metacto.core.ui.extensions.tintIfNotNull

@Composable
fun BaseTextButton(
    modifier: Modifier,
    text: String? = null,
    isEnabled: Boolean,
    color: Color,
    disabledColor: Color,
    textStyle: TextStyle,
    iconSize: Dp,
    iconColor: Color?,
    spacing: Dp,
    padding: PaddingValues,
    startIconPainter: Painter?,
    startIconVector: ImageVector?,
    endIconPainter: Painter?,
    endIconVector: ImageVector?,
    onClick: (() -> Unit)?
) {
    // Prepare color
    val colorState by animateColorAsState(
        if (isEnabled) color else disabledColor,
        label = "Text Color State"
    )

    // Content row
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = spacing,
            alignment = Alignment.CenterHorizontally
        ),
        modifier = modifier
            .padding(padding)
            .noRippleClickable {
                if (isEnabled) onClick?.invoke()
            }
    ) {
        // Render start icon if required
        if (startIconVector != null) {
            Image(
                imageVector = startIconVector,
                colorFilter = tintIfNotNull(iconColor),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize)
            )
        } else if (startIconPainter != null) {
            Image(
                painter = startIconPainter,
                colorFilter = tintIfNotNull(iconColor),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize)
            )
        }

        // Render the text
        Text(
            text = text.orEmpty(),
            style = textStyle,
            color = colorState,
            textAlign = TextAlign.Center
        )

        // Render end icon if required
        if (endIconVector != null) {
            Image(
                imageVector = endIconVector,
                colorFilter = tintIfNotNull(iconColor),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize)
            )
        } else if (endIconPainter != null) {
            Image(
                painter = endIconPainter,
                colorFilter = tintIfNotNull(iconColor),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}