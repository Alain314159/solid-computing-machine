package com.cerdita.app.domain.model

data class Message(
    val id: String,
    val roomId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val status: MessageStatus,
    val type: MessageType,
    val mediaUrl: String? = null
)

enum class MessageStatus {
    PENDING,
    SENT,
    DELIVERED,
    READ
}

enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    VOICE
}
