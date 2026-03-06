package com.cerdita.app.domain.model

sealed class Attachment {
    data class Image(
        val url: String,
        val thumbnailUrl: String? = null,
        val width: Int,
        val height: Int,
        val mimeType: String,
        val size: Long
    ) : Attachment()

    data class Video(
        val url: String,
        val thumbnailUrl: String? = null,
        val duration: Long,
        val width: Int,
        val height: Int,
        val mimeType: String,
        val size: Long
    ) : Attachment()

    data class Audio(
        val url: String,
        val duration: Long,
        val mimeType: String,
        val size: Long,
        val waveform: List<Int> = emptyList()
    ) : Attachment()

    data class File(
        val url: String,
        val fileName: String,
        val mimeType: String,
        val size: Long
    ) : Attachment()
}
