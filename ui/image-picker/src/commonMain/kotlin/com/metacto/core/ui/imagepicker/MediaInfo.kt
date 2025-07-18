package com.metacto.core.ui.imagepicker

data class MediaInfo(
    val data: ByteArray? = null,
    val type: MediaType,
    val filePath: String,
    val source: MediaInfoSource,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as MediaInfo

        if (!data.contentEquals(other.data)) return false
        if (type != other.type) return false
        if (filePath != other.filePath) return false
        if (source != other.source) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + filePath.hashCode()
        result = 31 * result + source.hashCode()
        return result
    }
}

enum class MediaType(val mimeType: String) {
    Image("image/*"),
    Video("video/*"),
}

enum class MediaInfoSource {
    Gallery,
    Camera
}

/**
 * Cleans up temporary files if they exist (iOS specific)
 */
expect fun MediaInfo.cleanupTemporaryFiles()