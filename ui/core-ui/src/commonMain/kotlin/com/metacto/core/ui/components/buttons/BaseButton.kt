package com.metacto.core.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.components.visibilities.FadeVisibility
import com.metacto.core.ui.extensions.tintIfNotNull
import com.metacto.core.ui.theme.CoreTheme.shapes
import com.metacto.core.ui.theme.CoreTheme.spacings
import com.metacto.core.ui.theme.CoreTheme.typography

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = typography.btnLabelMedium,
    textColor: Color,
    shape: Shape = shapes.small,
    minHeight: Dp = spacings.btnMinHeightNormal,
    backgroundColor: Color,
    disabledBackgroundColor: Color = backgroundColor.copy(alpha = 0.3f),
    border: BorderStroke? = null,
    iconSize: Dp = spacings.iconLarge,
    iconColor: Color?,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    loadingColor: Color = ProgressIndicatorDefaults.circularColor,
    contentSpacing: Dp = spacings.paddingXLarge,
    elevation: Dp = spacings.btnElevation,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    padding: PaddingValues = PaddingValues(
        vertical = spacings.btnPaddingVertical,
        horizontal = spacings.btnPaddingHorizontal
    ),
    onClick: () -> Unit = {}
) {
    // Prepare real values
    val realIsEnabled = isEnabled && isLoading.not()
    val realDisabledBgColor = if (isLoading) backgroundColor else disabledBackgroundColor
    val realBgColor = if (isDimmed) disabledBackgroundColor else backgroundColor

    // Render button
    WithoutMaterialTouchTargetPadding {
        Button(
            modifier = modifier.heightIn(min = minHeight),
            shape = shape,
            border = border,
            enabled = realIsEnabled,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = realBgColor,
                disabledContainerColor = realDisabledBgColor
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = elevation,
                pressedElevation = elevation,
                hoveredElevation = elevation
            ),
            contentPadding = padding
        ) {
            // Container box
            Box(
                contentAlignment = Alignment.Center
            ) {
                // Loading indicator
                FadeVisibility(visible = isLoading) {
                    CircularProgressIndicator(
                        color = loadingColor,
                        strokeWidth = spacings.btnLoadingStroke,
                        modifier = Modifier.size(spacings.btnLoadingSize)
                    )
                }

                // Content row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = contentSpacing,
                        alignment = contentAlignment
                    )
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

                    // Render text
                    FadeVisibility(
                        visible = isLoading.not(),
                        modifier = Modifier.weight(
                            weight = 1f,
                            fill = false
                        )
                    ) {
                        Text(
                            text = text.orEmpty(),
                            textAlign = TextAlign.Center,
                            color = textColor,
                            style = textStyle
                        )
                    }

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
        }
    }
}

@Composable
private fun WithoutMaterialTouchTargetPadding(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides Dp.Unspecified,
        content = content
    )
}