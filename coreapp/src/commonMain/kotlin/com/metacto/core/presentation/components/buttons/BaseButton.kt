package com.metacto.core.presentation.components.buttons

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.metacto.coreApp.MR
import com.metacto.core.presentation.components.visibilities.FadeVisibility
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.orZero
import com.metacto.core.utils.extensions.rememberLottieComposition
import com.metacto.core.utils.extensions.tintIfNotNull
import dev.icerock.moko.resources.AssetResource
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    textColor: Color,
    shape: Shape = CoreTheme.shapes.small,
    minHeight: Dp = CoreTheme.spacings.btnMinHeightNormal,
    backgroundColor: Color,
    disabledBackgroundColor: Color = backgroundColor.copy(alpha = 0.3f),
    border: BorderStroke? = null,
    iconSize: Dp = CoreTheme.spacings.iconLarge,
    iconColor: Color?,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    loadingAnimRes: AssetResource = MR.assets.loading_indicator_anim,
    contentSpacing: Dp = CoreTheme.spacings.paddingXLarge,
    elevation: Dp = CoreTheme.spacings.btnElevation,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.btnPaddingVertical,
        horizontal = CoreTheme.spacings.btnPaddingHorizontal
    ),
    onClick: () -> Unit = {}
) {
    // Prepare real values
    val realIsEnabled = isEnabled && isLoading.not()
    val realDisabledBgColor = if (isLoading) backgroundColor else disabledBackgroundColor
    val realBgColor = if (isDimmed) disabledBackgroundColor else backgroundColor

    // Render button
    Button(
        modifier = modifier
            .heightIn(min = minHeight + border?.width.orZero()),
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
                // Prepare composition
                val composition by rememberLottieComposition(loadingAnimRes)
                val progress = animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )

                // Then render lottie
                LottieAnimation(
                    composition = composition,
                    progress = { progress.value },
                    modifier = Modifier.size(
                        CoreTheme.spacings.btnLoadingSize
                    )
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