package com.metacto.core.presentation.components.inputFields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.DelayedLaunchedEffect
import com.metacto.core.utils.extensions.focusRequesterIfNotNull
import com.metacto.core.utils.extensions.rememberFocusRequester
import com.metacto.core.utils.extensions.removeAllNonNumeric

private const val DEF_REQUEST_FOCUS_DELAY = 250L

@Composable
fun OutlinedOtpInputField(
    modifier: Modifier = Modifier,
    pinCount: Int,
    text: String = "",
    onValueChange: ((String) -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = CoreTheme.colors.outlinedOtpInputFieldTextColor,
    backgroundColor: Color = CoreTheme.colors.outlinedOtpInputFieldBackgroundColor,
    textStyle: TextStyle = CoreTheme.typography.outlinedOtpInputFieldTextStyle,
    horizontalSpacing: Dp = CoreTheme.spacings.outlinedOtpInputFieldPinSpacing,
    requestFocus: Boolean = false,
    requestFocusDelay: Long = DEF_REQUEST_FOCUS_DELAY
) {
    // Prepare text value state
    var textState by remember {
        mutableStateOf(text)
    }

    // Prepare value change handler
    val valueChangeHandler: (String) -> Unit = remember {
        {
            // Get input text
            var inputText = it

            // If not handled enter, proceed with normal text handling
            // Handle allow digits only validation
            inputText = inputText.removeAllNonNumeric()

            // Handle length validation
            if (inputText.length > pinCount) {
                inputText = inputText.substring(0, pinCount)
            }

            // Then invoke value changed
            onValueChange?.invoke(inputText)

            // Update field state
            textState = it
        }
    }

    // Request focus if required
    var focusRequester: FocusRequester? = null
    if (requestFocus) {
        focusRequester = rememberFocusRequester()
        DelayedLaunchedEffect(
            key = requestFocus,
            delay = requestFocusDelay
        ) {
            focusRequester.requestFocus()
        }
    }

    // Container box
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Render basic field
        BasicTextField(
            modifier = Modifier.focusRequesterIfNotNull(focusRequester),
            value = textState,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = keyboardActions,
            onValueChange = valueChangeHandler,
            decorationBox = {
                // Container row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = horizontalSpacing,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    // Render digits
                    repeat(pinCount) { index ->
                        OtpDigit(
                            modifier = Modifier.weight(1f),
                            digit = text.getOrNull(index) ?: ' ',
                            textStyle = textStyle,
                            textColor = textColor,
                            backgroundColor = backgroundColor
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun OtpDigit(
    modifier: Modifier = Modifier,
    digit: Char,
    textStyle: TextStyle,
    textColor: Color,
    backgroundColor: Color,
    shape: RoundedCornerShape = CoreTheme.shapes.otpDigitShape,
    paddingValues: PaddingValues = PaddingValues(vertical = CoreTheme.spacings.otpDigitPaddingVertical)
) {
    // Render digit text
    Text(
        text = digit.toString(),
        style = textStyle,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .padding(paddingValues)
    )
}