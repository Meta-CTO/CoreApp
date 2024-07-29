package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.coroutines.launch

@Composable
fun InlineInputField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    scrollState: ScrollState = rememberScrollState(),
    insideLazyColumn: Boolean = false,
    lazyState: LazyGridState? = null,
    columnThreshold: Int = 8,
    focused: Boolean = false,
    textColor: Color = CoreTheme.colors.inlineInputFieldTextColor,
    textStyle: TextStyle = CoreTheme.typography.bodyMedium.copy(color = textColor),
    placeholderTextColor: Color = CoreTheme.colors.inlineInputFieldPlaceholderColor,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = FontsTransformation(textStyle),
    readOnly: Boolean = false,
    maxLength:Int = Int.MAX_VALUE,
    maxLines:Int = Int.MAX_VALUE,
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val defaultModifier =
        (if (insideLazyColumn.not()) modifier.verticalScroll(scrollState) else modifier).background(
            color = Color.Transparent
        )
    var lineCount by remember { mutableStateOf(1) }
    val lineHeight = CoreTheme.typography.bodyMedium.lineHeight.value * 4

    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = text, selection = TextRange(text.length)
            )
        )
    }
    if (textFieldValueState.text != text) {
        textFieldValueState = TextFieldValue(
            text = text, selection = TextRange(text.length)
        )
    }

    BasicTextField(
        modifier = if (focused) defaultModifier.focusRequester(focusRequester) else defaultModifier,
        value = textFieldValueState,
        onValueChange = {

            var inputText = it.text

            // Handle length validation
            if (inputText.length > maxLength) {
                inputText = inputText.take(maxLength)
            }

            onValueChange(inputText)
            textFieldValueState = it
        },
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            val currentLinesCount = textLayoutResult.lineCount
            if (currentLinesCount > columnThreshold) {
                if (lineCount < currentLinesCount) {
                    lineCount = currentLinesCount
                    coroutineScope.launch {
                        if (insideLazyColumn.not()) scrollState.scrollBy(lineHeight)
                        else lazyState?.scrollBy(lineHeight)
                    }
                }
            }
        },
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction, capitalization = capitalization
        ),
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        readOnly = readOnly,
        maxLines = maxLines,
        decorationBox = { innerTextField ->
            innerTextField()
            if (text.isEmpty()) {
                Row(
                    Modifier, verticalAlignment = Alignment.Top
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = placeholder,
                        style = textStyle,
                        maxLines = maxLines,
                        color = placeholderTextColor
                    )
                }
            }
        },
    )
    if (focused) LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

class FontsTransformation(private val textStyle: TextStyle) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithFonts(text.toString(), textStyle),
            OffsetMapping.Identity
        )
    }
}

fun buildAnnotatedStringWithFonts(
    text: String,
    textStyle: TextStyle,
): AnnotatedString {
    val words: List<String> = text.split("\\s+".toRegex())// splits by whitespace

    val builder = AnnotatedString.Builder()
    // loop the words list and check if contains # or @ to make it bold else it shall be normal font
    words.forEachIndexed { index, word ->
        builder.withStyle(
            style =
            if (word.startsWith("@") || word.startsWith("#")) {
                textStyle.copy(fontWeight = FontWeight.ExtraBold).toSpanStyle()
            } else {
                textStyle.toSpanStyle()
            }
        ) {
            // append each word
            append(word)
            if (index < words.lastIndex)
                append(" ")
        }
    }
    return builder.toAnnotatedString()
}