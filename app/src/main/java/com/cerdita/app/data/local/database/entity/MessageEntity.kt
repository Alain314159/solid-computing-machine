package com.cerdita.app.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val messageId: String,
    val roomId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val status: String, // pending, sent, delivered, read
    val type: String, // text, image, video, voice
    val mediaUrl: String? = null
)
