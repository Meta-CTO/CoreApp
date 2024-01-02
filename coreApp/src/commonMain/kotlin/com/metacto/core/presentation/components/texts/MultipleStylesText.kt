package com.metacto.core.presentation.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun MultipleStylesText(
    strings: List<Pair<SpanStyle, String>>,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            strings.forEach { item ->
                withStyle(style = item.first) { append(item.second) }
            }
        },
        textAlign = textAlign,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}
