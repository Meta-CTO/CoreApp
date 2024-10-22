package com.sampleApp.app.presentation.main.components.navBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.utils.extensions.noRippleClickable
import com.sampleApp.app.presentation.theme.AppTheme.colors
import com.sampleApp.app.presentation.theme.AppTheme.spacings
import com.sampleApp.app.presentation.theme.AppTheme.typography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun NavigationItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Prepare values
    val tabColor = if (isSelected) colors.terra else colors.black

    // Container column
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacings.spacing2),
        modifier = modifier.noRippleClickable(onClick = onClick)
    ) {
        // Icon
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(tabColor),
            modifier = Modifier.size(spacings.spacing24)
        )

        // Label
        SingleLineText(
            text = title,
            style = typography.quasimodaSemiBold14,
            color = tabColor
        )
    }
}