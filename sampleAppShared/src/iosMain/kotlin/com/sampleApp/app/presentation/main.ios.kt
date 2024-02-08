package com.sampleApp.app.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.metacto.core.presentation.components.options.OptionsList
import com.metacto.core.presentation.options.models.OptionUIModel

fun MainViewController() = ComposeUIViewController {
    // Then render app
//    AppContent()

    val options = listOf(
        OptionUIModel(
            id = 1,
            title = "Option 1"
        ),
        OptionUIModel(
            id = 2,
            title = "Option 2"
        ),
        OptionUIModel(
            id = 3,
            title = "Option 3"
        ),
        OptionUIModel(
            id = 4,
            title = "Option 4"
        ),
        OptionUIModel(
            id = 5,
            title = "Option 5"
        ),
        OptionUIModel(
            id = 6,
            title = "Option 6"
        ),
        OptionUIModel(
            id = 7,
            title = "Option 7"
        ),
        OptionUIModel(
            id = 8,
            title = "Option 8"
        ),
        OptionUIModel(
            id = 9,
            title = "Option 9"
        ),
        OptionUIModel(
            id = 10,
            title = "Option 10"
        ),
        OptionUIModel(
            id = 11,
            title = "Option 11"
        ),
        OptionUIModel(
            id = 12,
            title = "Option 12"
        ),
        OptionUIModel(
            id = 13,
            title = "Option 13"
        ),
        OptionUIModel(
            id = 14,
            title = "Option 14"
        ),
        OptionUIModel(
            id = 15,
            title = "Option 15"
        ),
        OptionUIModel(
            id = 16,
            title = "Option 16"
        ),
        OptionUIModel(
            id = 17,
            title = "Option 17"
        ),
        OptionUIModel(
            id = 18,
            title = "Option 18"
        ),
        OptionUIModel(
            id = 19,
            title = "Option 19"
        ),
        OptionUIModel(
            id = 20,
            title = "Option 20"
        ),
        OptionUIModel(
            id = 21,
            title = "Option 21"
        ),

    )


    OptionsList(
        modifier = Modifier.fillMaxWidth(),
        options = options,
        onItemClick = {
            print("Item clicked, $it")
        }
    )
}