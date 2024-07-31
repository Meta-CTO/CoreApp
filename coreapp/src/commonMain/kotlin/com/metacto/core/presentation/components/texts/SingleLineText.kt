package com.metacto.core.presentation.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun SingleLineText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = CoreTheme.typography.singleLineTextStyle,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    isUnderlined: Boolean = false,
    isUpperCase: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    // Prepare the modifier
    val myModifier = Modifier
        .noRippleClickable { onClick?.invoke() }
        .takeIf { onClick != null }
        ?.then(modifier)
        ?: modifier

    // Prepare the text
    val myText = remember(text) {
        if (isUpperCase) text.uppercase() else text
    }

    Text(
        text = myText,
        style = style,
        color = color,
        textAlign = textAlign,
        textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = myModifier
    )
}