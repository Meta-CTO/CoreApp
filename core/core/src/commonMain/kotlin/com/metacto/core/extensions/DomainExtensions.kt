package com.metacto.core.extensions

import com.metacto.kmm.network.model.media.Media

val Media.isVideo: Boolean
    get() {
        val fileURL = getMediaUrl() ?: ""
        val fileExtension = fileURL.substringAfterLast('.', "").lowercase()
        val videoExtensions = listOf("mp4", "mov", "avi", "wmv", "flv", "mkv", "webm")
        return videoExtensions.contains(fileExtension)
    }