package com.metacto.core.ui.globalState.models

import com.metacto.core.ui.resources.Res
import com.metacto.core.ui.resources.ic_upgrade
import org.jetbrains.compose.resources.DrawableResource

data class ForceUpdatePopupParams(
    val isRequired: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val image: DrawableResource? = Res.drawable.ic_upgrade,
    val updateButtonText: String? = null,
    val skipUpdateButtonText: String? = null,
    val onUpdateClick: (() -> Unit)? = null,
    val onSkipUpdateClicked: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)