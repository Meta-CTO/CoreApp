package com.metacto.core.ui.components.texts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme
import com.metacto.core.ui.extensions.tintIfNotNull

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    text: String,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    isUnderlined: Boolean = false,
    isSingleLine: Boolean = false,
    color: Color = CoreTheme.colors.iconText.color,
    iconTint: Color? = color,
    iconSize: Dp = CoreTheme.spacings.iconText.iconSize,
    iconSpacing: Dp = CoreTheme.spacings.iconText.iconSpacing,
    style: TextStyle = CoreTheme.typography.iconText.textStyle,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    textAlign: TextAlign = TextAlign.Center
) {
    // Container row
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
    ) {
        // Render start icon if possible
        if (startIconVector != null) {
            Image(
                imageVector = startIconVector,
                colorFilter = tintIfNotNull(iconTint),
                contentDescription = text,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(iconSpacing))
        } else if (startIconPainter != null) {
            Image(
                painter = startIconPainter,
                colorFilter = tintIfNotNull(iconTint),
                contentDescription = text,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(iconSpacing))
        }

        // Render text
        Text(
            text = text,
            style = style,
            color = color,
            textAlign = textAlign,
            textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
            maxLines = if (isSingleLine) 1 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(weight = 1f, fill = false)
        )

        // Render end icon if possible
        if (endIconVector != null) {
            Spacer(modifier = Modifier.width(iconSpacing))
            Image(
                imageVector = endIconVector,
                colorFilter = tintIfNotNull(iconTint),
                contentDescription = text,
                modifier = Modifier.size(iconSize)
            )
        } else if (endIconPainter != null) {
            Spacer(modifier = Modifier.width(iconSpacing))
            Image(
                painter = endIconPainter,
                colorFilter = tintIfNotNull(iconTint),
                contentDescription = text,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}