package com.metacto.core.ui.components.grids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun NonLazyGrid(
    modifier: Modifier = Modifier,
    columnsCount: Int,
    verticalSpacing: Dp = CoreTheme.spacings.nonLazyGrid.verticalSpacing,
    horizontalSpacing: Dp = CoreTheme.spacings.nonLazyGrid.horizontalSpacing,
    items: List<@Composable () -> Unit>
) {
    // This column makes the grid scrollable vertically
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        // Split the items into rows based on the column count
        items.chunked(columnsCount).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(
                    space = horizontalSpacing,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                for (item in rowItems) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f) // Make each item take equal width
                    ) {
                        item()
                    }
                }
                // Fill the remaining space if the last row has fewer items than the column count
                if (rowItems.size < columnsCount) {
                    repeat(columnsCount - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}