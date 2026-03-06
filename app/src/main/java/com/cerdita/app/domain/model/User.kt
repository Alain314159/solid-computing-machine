package com.cerdita.app.domain.model

data class User(
    val id: String,
    val displayName: String,
    val avatarUrl: String? = null,
    val email: String? = null,
    val status: UserStatus = UserStatus.ONLINE,
    val lastSeen: Long = System.currentTimeMillis()
)

enum class UserStatus {
    ONLINE,
    OFFLINE,
    BUSY,
    AWAY
}
