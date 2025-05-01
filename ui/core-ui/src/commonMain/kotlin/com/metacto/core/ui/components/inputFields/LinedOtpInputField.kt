package com.metacto.core.ui.components.inputFields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.extensions.removeAllNonNumeric
import com.metacto.core.ui.theme.CoreTheme
import com.metacto.core.ui.extensions.DelayedLaunchedEffect
import com.metacto.core.ui.extensions.focusRequesterIfNotNull
import com.metacto.core.ui.extensions.rememberFocusRequester

private const val DEF_REQUEST_FOCUS_DELAY = 250L

@Composable
fun LinedOtpInputField(
    modifier: Modifier = Modifier,
    pinCount: Int,
    text: String = "",
    onValueChange: ((String) -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = CoreTheme.colors.linedOtpInputField.textColor,
    borderColor: Color = CoreTheme.colors.linedOtpInputField.borderColor,
    textStyle: TextStyle = CoreTheme.typography.linedOtpInputField.textStyle,
    requestFocus: Boolean = false,
    requestFocusDelay: Long = DEF_REQUEST_FOCUS_DELAY,
    horizontalSpacing: Dp = CoreTheme.spacings.linedOtpInputField.horizontalSpacing
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
                            borderColor = borderColor
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
    borderColor: Color,
    lineSize: Dp = CoreTheme.spacings.otpDigit.lineSize,
) {
    // Container column
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Render digit text
        Text(
            text = digit.toString(),
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Border line
        Box(
            modifier = Modifier
                .background(borderColor)
                .height(lineSize)
                .fillMaxWidth()
        )
    }
}