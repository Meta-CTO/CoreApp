package com.metacto.catalogapp.presentation.customDialog.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.ui.components.buttons.PrimaryFilledButton

@Composable
internal fun ComplexDialog(
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Complex Dialog Example",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "This dialog demonstrates complete control over the content. You can add any UI elements, including custom buttons.",
            style = MaterialTheme.typography.bodyMedium
        )

        // Custom buttons in the content
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryFilledButton(
                text = "Primary Action",
                onClick = onPrimaryClick,
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryFilledButton(
                text = "Secondary Action",
                onClick = onSecondaryClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}