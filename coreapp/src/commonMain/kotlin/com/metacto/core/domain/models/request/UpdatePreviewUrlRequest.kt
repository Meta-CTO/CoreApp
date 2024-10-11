package com.metacto.core.domain.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePreviewUrlRequest(
    @SerialName("data")
    val data: UpdatePreviewUrlRequestData
)

@Serializable
data class UpdatePreviewUrlRequestData(
    @SerialName("previewUrl")
    val previewUrl: String
)