package com.cerdita.app.domain.model

data class Room(
    val id: String,
    val name: String? = null,
    val alias: String? = null,
    val topic: String? = null,
    val avatarUrl: String? = null,
    val isDirect: Boolean = false,
    val lastMessage: Message? = null,
    val unreadCount: Int = 0,
    val members: List<String> = emptyList()
)
