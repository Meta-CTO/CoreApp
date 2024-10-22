package com.sampleApp.app.presentation.main.components.navBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import com.sampleApp.app.resources.*
import com.sampleApp.app.presentation.theme.AppTheme.colors
import com.sampleApp.app.presentation.theme.AppTheme.spacings

@Composable
internal fun NavigationBar(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onItemClick: (Int) -> Unit
) {
    // Container row
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = spacings.spacing24,
                spotColor = colors.black,
                ambientColor = colors.black
            )
            .background(colors.white)
            .padding(vertical = spacings.spacing8)
            .then(modifier)
    ) {
        NavigationItem(
            icon = Res.drawable.ic_star_filled,
            title = "Home",
            isSelected = selectedTab == 0,
            onClick = {
                onItemClick.invoke(0)
            }
        )

        NavigationItem(
            icon = Res.drawable.ic_star_filled,
            title = "Profile",
            isSelected = selectedTab == 1,
            onClick = {
                onItemClick.invoke(1)
            }
        )
    }
}