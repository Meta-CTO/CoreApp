package com.metacto.core.presentation.globalState.models

import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.asCommon
import com.metacto.coreApp.MR

data class ForceUpdatePopupParams(
    val isRequired: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val image: CommonImageResource = MR.images.ic_upgrade.asCommon(),
    val updateButtonText: String? = null,
    val ignoreUpdateButtonText: String? = null,
    val onUpdateClick: (() -> Unit)? = null,
    val onIgnoreClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)