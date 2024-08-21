package com.metacto.core.domain.repos.forceUpdate

data class ForceUpdateResponse(
    val message: String,
    val isRequired: Boolean,
    val iosAppStoreId: String
)