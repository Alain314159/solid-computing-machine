package com.cerdita.app.util

object Constants {
    const val DATABASE_NAME = "cerdita_database"
    const val SHARED_PREFS_NAME = "cerdita_secure_prefs"
    const val MATRIX_HOMESERVER = "https://matrix-client.matrix.org"
    
    // Message status
    const val STATUS_PENDING = "pending"
    const val STATUS_SENT = "sent"
    const val STATUS_DELIVERED = "delivered"
    const val STATUS_READ = "read"
    
    // Message types
    const val TYPE_TEXT = "text"
    const val TYPE_IMAGE = "image"
    const val TYPE_VIDEO = "video"
    const val TYPE_VOICE = "voice"
}
