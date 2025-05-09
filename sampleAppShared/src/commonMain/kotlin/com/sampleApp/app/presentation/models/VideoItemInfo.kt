package com.sampleApp.app.presentation.models

data class VideoItemInfo(
    val url: String,
    val artist: String? = null,
    val title: String? = null,
    val artworkUrl: String? = null,
)