package com.metacto.core.presentation.components.inputFields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.DelayedLaunchedEffect
import com.metacto.core.utils.extensions.focusRequesterIfNotNull
import com.metacto.core.utils.extensions.noRippleClickable
import com.metacto.core.utils.extensions.rememberFocusRequester
import com.metacto.core.utils.extensions.removeAllNonNumeric
import com.metacto.core.utils.extensions.toDp

private const val REQUEST_FOCUS_DELAY = 250L

@Composable
fun BaseTextInputField(
    text: String,
    label: String?,
    isStaticLabel: Boolean,
    onValueChange: ((String) -> Unit)?,
    onClick: (() -> Unit)?,
    modifier: Modifier,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    capitalization: KeyboardCapitalization,
    backgroundColor: Color,
    focusedBorderColor: Color,
    unFocusedBorderColor: Color,
    singleLine: Boolean,
    enabled: Boolean,
    readOnly: Boolean,
    visualTransformation: VisualTransformation,
    textColor: Color,
    placeholderTextColor: Color,
    labelTextColor: Color,
    errorTextColor: Color,
    maxLength: Int,
    maxLines: Int,
    minHeight: Dp,
    error: String?,
    endIconVector: ImageVector?,
    endIconPainter: Painter?,
    endIconSize: Dp,
    onEndIconClick: (() -> Unit)?,
    startIconVector: ImageVector?,
    startIconPainter: Painter?,
    startIconSize: Dp,
    onStartIconClick: (() -> Unit)?,
    iconTintColor: Color,
    placeholder: String?,
    placeholderMaxLines: Int,
    shape: RoundedCornerShape,
    placeholderTextStyle: TextStyle,
    textStyle: TextStyle,
    textAlign: TextAlign?,
    labelTextStyle: TextStyle,
    errorTextStyle: TextStyle,
    allowDigitsOnly: Boolean,
    shadowColor: Color = CoreTheme.colors.black,
    elevation: Dp = CoreTheme.spacings.inputFieldElevation,
    requestFocus: Boolean
) {
    // Prepare field box size state
    var fieldBoxSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    // Prepare text value state
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

    // Prepare leading image
    val leadingImage: @Composable (() -> Unit)? = if (startIconVector != null) {
        {
            Image(
                imageVector = startIconVector,
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(startIconSize)
                    .noRippleClickable(onClick = onStartIconClick)
            )
        }
    } else if (startIconPainter != null) {
        {
            Image(
                painter = startIconPainter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(startIconSize)
                    .noRippleClickable(onClick = onStartIconClick)
            )
        }
    } else null

    // Prepare trailing image
    val trailingImage: @Composable (() -> Unit)? = if (endIconVector != null) {
        {
            Image(
                imageVector = endIconVector,
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(endIconSize)
                    .noRippleClickable(onClick = onEndIconClick)
            )
        }
    } else if (endIconPainter != null) {
        {
            Image(
                painter = endIconPainter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(endIconSize)
                    .noRippleClickable(onClick = onEndIconClick)
            )
        }
    } else null

    // Prepare placeholder text
    val placeholderText: @Composable (() -> Unit)? = if (placeholder != null) {
        {
            Text(
                text = placeholder,
                style = placeholderTextStyle,
                color = placeholderTextColor,
                maxLines = placeholderMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else null

    // Prepare label text
    val labelText: @Composable (() -> Unit)? = if (isStaticLabel.not() && label != null) {
        {
            Text(
                text = label,
                style = labelTextStyle,
                color = labelTextColor,
            )
        }
    } else null

    // Prepare value change handler
    val valueChangeHandler: (TextFieldValue) -> Unit = remember {
        {
            // Get input text
            var inputText = it.text

            // Handle allow digits only validation
            if (allowDigitsOnly) {
                inputText = inputText.removeAllNonNumeric()
            }

            // Handle length validation
            if (inputText.length > maxLength) {
                inputText = inputText.substring(0, maxLength)
            }

            // Then invoke value changed
            onValueChange?.invoke(inputText)

            // Update field state
            textFieldValueState = it
        }
    }

    // Request focus if required
    var focusRequester: FocusRequester? = null
    if (requestFocus) {
        focusRequester = rememberFocusRequester()
        DelayedLaunchedEffect(
            key = requestFocus,
            delay = REQUEST_FOCUS_DELAY
        ) {
            focusRequester.requestFocus()
        }
    }

    // Container column
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(CoreTheme.spacings.paddingSmall)
    ) {
        // Render static label if required
        if (label != null && isStaticLabel) {
            Text(
                text = label,
                style = labelTextStyle,
                color = labelTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Render text field box container
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { fieldBoxSize = it }
                .shadow(
                    elevation = elevation,
                    spotColor = shadowColor,
                    ambientColor = shadowColor,
                    shape = shape
                )
        ) {
            // Render text field
            OutlinedTextField(
                textStyle = textStyle.copy(
                    textAlign = textAlign ?: TextAlign.Start
                ),
                readOnly = readOnly,
                value = textFieldValueState,
                isError = error != null,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                enabled = enabled,
                onValueChange = valueChangeHandler,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    capitalization = capitalization
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedSupportingTextColor = textColor,
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                    disabledContainerColor = backgroundColor,
                    errorContainerColor = backgroundColor,
                    focusedIndicatorColor = focusedBorderColor,
                    unfocusedIndicatorColor = unFocusedBorderColor,
                    disabledIndicatorColor = focusedBorderColor.copy(alpha = 0.5f),
                    errorIndicatorColor = CoreTheme.colors.danger
                ),
                shape = shape,
                visualTransformation = visualTransformation,
                leadingIcon = leadingImage,
                trailingIcon = trailingImage,
                label = labelText,
                placeholder = placeholderText,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .focusRequesterIfNotNull(focusRequester)
            )

            // Check if we need clickable layer
            if (onClick != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldBoxSize.height.toDp())
                        .noRippleClickable(onClick = onClick)
                )
            }
        }

        // Render error if required
        AnimatedVisibility(error != null) {
            Text(
                text = error.orEmpty(),
                style = errorTextStyle,
                color = errorTextColor,
                maxLines = placeholderMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}